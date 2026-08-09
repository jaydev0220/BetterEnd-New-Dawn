package org.betterx.betterend.client;

import org.betterx.betterend.particle.FireflyParticle;
import org.betterx.betterend.particle.InfusionMistParticle;
import org.betterx.betterend.particle.InfusionParticle;
import org.betterx.betterend.particle.ParticleBlackSpore;
import org.betterx.betterend.particle.ParticleGeyser;
import org.betterx.betterend.particle.ParticleGlowingSphere;
import org.betterx.betterend.particle.ParticleJungleSpore;
import org.betterx.betterend.particle.ParticleSnowflake;
import org.betterx.betterend.particle.ParticleSulphur;
import org.betterx.betterend.particle.ParticleTenaneaPetal;
import org.betterx.betterend.particle.PaticlePortalSphere;
import org.betterx.betterend.particle.SmaragdantParticle;
import org.betterx.betterend.registry.EndParticles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

final class EndParticleProviders {
    private EndParticleProviders() {
    }

    static void register() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
        registry.register(EndParticles.GLOWING_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        registry.register(EndParticles.PORTAL_SPHERE, PaticlePortalSphere.FactoryPortalSphere::new);
        registry.register(EndParticles.INFUSION, InfusionParticle.InfusionFactory::new);
        registry.register(EndParticles.SULPHUR_PARTICLE, ParticleSulphur.FactorySulphur::new);
        registry.register(EndParticles.GEYSER_PARTICLE, ParticleGeyser.FactoryGeyser::new);
        registry.register(EndParticles.SNOWFLAKE, ParticleSnowflake.FactorySnowflake::new);
        registry.register(EndParticles.AMBER_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        registry.register(EndParticles.BLACK_SPORE, ParticleBlackSpore.FactoryBlackSpore::new);
        registry.register(EndParticles.TENANEA_PETAL, ParticleTenaneaPetal.FactoryTenaneaPetal::new);
        registry.register(EndParticles.JUNGLE_SPORE, ParticleJungleSpore.FactoryJungleSpore::new);
        registry.register(EndParticles.FIREFLY, FireflyParticle.FireflyParticleFactory::new);
        registry.register(EndParticles.SMARAGDANT, SmaragdantParticle.SmaragdantParticleFactory::new);
        registry.register(EndParticles.INFUSION_MIST, InfusionMistParticle.FactoryInfusionMist::new);
        registry.register(
                EndParticles.INFUSION_MIST_NORTH,
                sprites -> new InfusionMistParticle.FactoryInfusionMist(sprites, InfusionMistParticle.NORTH_RGB)
        );
    }
}
