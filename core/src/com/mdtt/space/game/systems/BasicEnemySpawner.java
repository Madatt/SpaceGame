package com.mdtt.space.game.systems;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.mdtt.space.Assets;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.entities.enemies.Asteroid;
import com.mdtt.space.entities.enemies.BlueBasic;
import com.mdtt.space.entities.enemies.Rotor;
import com.mdtt.space.game.GameTimer;
import com.mdtt.space.screens.CommonScreen;
import com.mdtt.space.screens.SpaceGameScreen;

import java.util.ArrayList;
import java.util.Random;

public class BasicEnemySpawner extends GameSystem {
    public static int rotorNumber = 5;
    public static int blueBasicsNumber = 2;
    public static long defaultSpawnDelay = 2000;

    private com.mdtt.space.game.EntityFactory factory;

    private Random random;

    private GameTimer asteroidTimer = new GameTimer();
    private GameTimer rotorTimer = new GameTimer();
    private GameTimer blueBasicTimer = new GameTimer();

    private EnemyContainer rotors;
    private EnemyContainer blueBasics;

    public BasicEnemySpawner(Assets assets, SpaceGameScreen screen) {
        super(assets, screen);
        this.factory = new EntityFactory(assets, screen);
        rotors = new EnemyContainer(assets, screen);
        blueBasics = new EnemyContainer(assets, screen);

        rotorTimer.restart(defaultSpawnDelay);
        blueBasicTimer.restart(defaultSpawnDelay);
        asteroidTimer.restart(randomRange(Asteroid.respawnMin, Asteroid.respawnMax));
    }

    private static long randomRange(long a, long b) {
        Random random = new Random();
        return random.nextInt((int) (b - a))
                + a;
    }

    private static int randomRange(int a, int b) {
        Random random = new Random();
        return random.nextInt((b - a)) + a;
    }

    private void spawnAsteroid() {
        if (asteroidTimer.elapsed()) {
            asteroidTimer.restart(randomRange(Asteroid.respawnMin, Asteroid.respawnMax));

            factory.createAsteroid(CommonScreen.width / 2.f +
                            randomRange(-CommonScreen.width / 3, CommonScreen.width / 3),
                    CommonScreen.height + 50);
        }
    }

    private void spawnRotors() {
        if (rotors.allDead() && !rotorTimer.running()) {
            rotorTimer.restart(defaultSpawnDelay);
        } else if (rotors.allDead() && rotorTimer.elapsed()) {
            rotorTimer.stop();
            for (int i = 0; i <= rotorNumber; i++) {
                Gdx.app.log("Spawner", "Spawn rotors");
                float angle = 180.f / rotorNumber * i;
                float radius = CommonScreen.width / 3.f;
                Rotor rotor = factory.createRotor(0, 0);
                rotor.x = CommonScreen.width / 2.f;
                rotor.y = CommonScreen.height + 300;
                rotor.addWaypoint(CommonScreen.width / 2.0 + Math.cos(Math.toRadians(angle)) * radius,
                        CommonScreen.height - radius * 1.5 - Math.sin(Math.toRadians(angle)) * radius,
                        3000);
                rotor.startInactive();
                rotors.add(rotor);
            }
        }
    }

    public void spawnBlueBasics() {
        if(blueBasics.allDead() && !blueBasicTimer.running()) {
            blueBasicTimer.restart(defaultSpawnDelay);
        } else if(blueBasics.allDead() && blueBasicTimer.elapsed()) {
            BlueBasic blueBasic = factory.createBlueBasic(0, 0);
            blueBasic.x = CommonScreen.width / 2.f;
            blueBasic.y = CommonScreen.height + 300;
            blueBasic.addWaypoint(CommonScreen.width / 2.f - 128, CommonScreen.height - 256,
                    2000);
            blueBasic.addWaypoint(CommonScreen.width / 2.f + 128, CommonScreen.height - 256,
                    2000);
            blueBasic.loop();
            blueBasic.startInactive();
            blueBasics.add(blueBasic);
        }
    }

    public void step(float delta) {
        rotors.step(delta);
        blueBasics.step(delta);

        spawnAsteroid();
        spawnRotors();
        spawnBlueBasics();
    }
}
