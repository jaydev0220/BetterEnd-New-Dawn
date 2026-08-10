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

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

final class EndParticleProviders {
    private EndParticleProviders() {
    }

    static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(EndParticles.GLOWING_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        event.registerSpriteSet(EndParticles.PORTAL_SPHERE, PaticlePortalSphere.FactoryPortalSphere::new);
        event.registerSpriteSet(EndParticles.INFUSION, InfusionParticle.InfusionFactory::new);
        event.registerSpriteSet(EndParticles.SULPHUR_PARTICLE, ParticleSulphur.FactorySulphur::new);
        event.registerSpriteSet(EndParticles.GEYSER_PARTICLE, ParticleGeyser.FactoryGeyser::new);
        event.registerSpriteSet(EndParticles.SNOWFLAKE, ParticleSnowflake.FactorySnowflake::new);
        event.registerSpriteSet(EndParticles.AMBER_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        event.registerSpriteSet(EndParticles.BLACK_SPORE, ParticleBlackSpore.FactoryBlackSpore::new);
        event.registerSpriteSet(EndParticles.TENANEA_PETAL, ParticleTenaneaPetal.FactoryTenaneaPetal::new);
        event.registerSpriteSet(EndParticles.JUNGLE_SPORE, ParticleJungleSpore.FactoryJungleSpore::new);
        event.registerSpriteSet(EndParticles.FIREFLY, FireflyParticle.FireflyParticleFactory::new);
        event.registerSpriteSet(EndParticles.SMARAGDANT, SmaragdantParticle.SmaragdantParticleFactory::new);
        event.registerSpriteSet(EndParticles.INFUSION_MIST, InfusionMistParticle.FactoryInfusionMist::new);
        event.registerSpriteSet(
                EndParticles.INFUSION_MIST_NORTH,
                sprites -> new InfusionMistParticle.FactoryInfusionMist(sprites, InfusionMistParticle.NORTH_RGB)
        );
    }
}

