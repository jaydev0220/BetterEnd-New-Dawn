#!/usr/bin/env bash
# Usage: bash build-local.sh [Gradle task/options]
# Override dependency locations with BCLIB_JAR, WUNDERLIB_JAR, WORLDWEAVER_JAR,
# JEI_JAR, JADE_JAR, or WOVER_API_DIR when the standard Mod12111 layout differs.
set -euo pipefail
shopt -s nullglob

project_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd -P)"
dependency_root="$(cd -- "$project_dir/.." && pwd -P)"
override_dir="$project_dir/override"
created_override_paths=()
created_override_dir=false

cleanup() {
    local override_path
    for override_path in "${created_override_paths[@]}"; do
        rm -f -- "$override_path"
    done
    if [[ "$created_override_dir" == true ]]; then
        rmdir -- "$override_dir" 2>/dev/null || true
    fi
}
trap cleanup EXIT

property_value() {
    local property_name="$1"
    awk -F= -v key="$property_name" '$1 == key { print $2; exit }' "$project_dir/gradle.properties"
}

resolve_jar() {
    local label="$1"
    local explicit_path="$2"
    shift 2

    if [[ -n "$explicit_path" ]]; then
        if [[ -f "$explicit_path" && "$explicit_path" != *-sources.jar ]]; then
            printf '%s' "$explicit_path"
            return
        fi
    else
        local candidate
        for candidate in "$@"; do
            if [[ -f "$candidate" && "$candidate" != *-sources.jar ]]; then
                printf '%s' "$candidate"
                return
            fi
        done
    fi

    printf 'Missing %s JAR. Set %s_JAR or place it under %s.\n' "$label" "${label^^}" "$dependency_root" >&2
    exit 1
}

find_cached_jar() {
    local cache_path="$1"
    local jar_name="$2"
    find "$cache_path" -type f -name "$jar_name" ! -name '*-sources.jar' -print -quit 2>/dev/null || true
}

bclib_version="$(property_value bclib_version)"
wover_version="$(property_value wover_version)"
wunderlib_version="$(property_value wunderlib_version)"
jei_version="$(property_value jei_version)"
jade_version="$(property_value jade_version)"

bclib_jar="$(resolve_jar BCLIB "${BCLIB_JAR:-}" \
    "$dependency_root/BCLib-New-Dawn/build/libs/bclib-"*.jar \
    "$dependency_root/BCLib-New-Dawn/build/devlibs/bclib-"*.jar \
    "$dependency_root/local-mods/bclib-"*.jar)"
wunderlib_jar="$(resolve_jar WUNDERLIB "${WUNDERLIB_JAR:-}" \
    "$dependency_root/wunderlib-"*.jar \
    "$dependency_root/local-mods/wunderlib-"*.jar)"
worldweaver_jar="$(resolve_jar WORLDWEAVER "${WORLDWEAVER_JAR:-}" \
    "$dependency_root/worldweaver-"*.jar \
    "$dependency_root/local-mods/worldweaver-"*.jar)"

gradle_cache_root="${GRADLE_USER_HOME:-${HOME}/.gradle}/caches/modules-2/files-2.1"
jei_jar="${JEI_JAR:-}"
if [[ -z "$jei_jar" ]]; then
    jei_jar="$(find_cached_jar \
        "$gradle_cache_root/mezz.jei/jei-1.21.11-fabric-api/$jei_version" \
        "jei-1.21.11-fabric-api-$jei_version.jar")"
fi
jade_jar="${JADE_JAR:-}"
if [[ -z "$jade_jar" ]]; then
    jade_jar="$(find_cached_jar \
        "$gradle_cache_root/maven.modrinth/jade/$jade_version" \
        "jade-$jade_version.jar")"
fi

if [[ ! -f "$jei_jar" || ! -f "$jade_jar" ]]; then
    printf 'Missing cached JEI/Jade API JAR. Set JEI_JAR and JADE_JAR explicitly.\n' >&2
    exit 1
fi

wover_api_dir="${WOVER_API_DIR:-$dependency_root/BCLib-New-Dawn/.gradle/loom-cache/remapped_mods/remapped/unspecified}"
wover_api_count="$(find "$wover_api_dir" -type f -name 'wover-*-api-*.jar' -print 2>/dev/null | wc -l)"
if (( wover_api_count == 0 )); then
    printf 'Missing remapped WorldWeaver API modules under %s. Set WOVER_API_DIR explicitly.\n' "$wover_api_dir" >&2
    exit 1
fi

if [[ -e "$override_dir" && ! -d "$override_dir" ]]; then
    printf 'Override path is not a directory: %s\n' "$override_dir" >&2
    exit 1
fi
if [[ ! -d "$override_dir" ]]; then
    mkdir -- "$override_dir"
    created_override_dir=true
fi

link_override() {
    local source_path="$1"
    local target_name="$2"
    local override_path="$override_dir/$target_name"
    if [[ -e "$override_path" || -L "$override_path" ]]; then
        printf 'Override file already exists: %s\n' "$override_path" >&2
        exit 1
    fi
    ln -s -- "$source_path" "$override_path"
    created_override_paths+=("$override_path")
}

link_override "$bclib_jar" "bclib-$bclib_version.jar"
link_override "$wunderlib_jar" "wunderlib-$wunderlib_version.jar"
link_override "$worldweaver_jar" "worldweaver-$wover_version.jar"
link_override "$jei_jar" "jei-1.21.11-fabric-api-$jei_version.jar"
link_override "$jade_jar" "jade-$jade_version.jar"

printf 'Local dependencies prepared; running Gradle.\n'
if (( $# == 0 )); then
    set -- remapJar --offline --no-daemon --console=plain
fi
cd "$project_dir"
bash ./gradlew "-Pbetterend_wover_api_dir=$wover_api_dir" "$@"
