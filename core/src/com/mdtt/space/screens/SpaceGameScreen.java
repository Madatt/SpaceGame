package com.mdtt.space.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mdtt.space.Assets;
import com.mdtt.space.SpaceGame;
import com.mdtt.space.entities.decor.Background;
import com.mdtt.space.entities.decor.Text;
import com.mdtt.space.entities.common.Bullet;
import com.mdtt.space.entities.common.Entity;
import com.mdtt.space.game.systems.BasicEnemySpawner;
import com.mdtt.space.game.EntityFactory;
import com.mdtt.space.entities.Player;
import com.mdtt.space.entities.common.Ship;
import com.mdtt.space.entities.enemies.Asteroid;
import com.mdtt.space.entities.enemies.Enemy;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class SpaceGameScreen extends CommonScreen implements Screen {
    private Player player;
    private Background background;
    private Text textHealth;
    private Text textScore;
    private Text textStart;

    private float lastAccX, accX, smoothAccX;
    private int center = width / 2;
    private ArrayList<Entity> entities;
    private Rectangle area;
    private long end = 0;
    private EntityFactory factory;
    private BasicEnemySpawner spawner;

    private Random random = new Random();
    private int score = 0;

    SpaceGameScreen(Game game, Assets assets) {
        super(game, assets);
        this.assets = assets;

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);

        player = new Player(assets, this);
        player.x = center;
        player.y = player.getBox().height / 2;

        entities = new ArrayList<>();
        factory = new EntityFactory(assets, this);
        background = new Background(assets, this);
        area = new Rectangle(0, 0, width, height);
        spawner = new BasicEnemySpawner(assets, this);

        //Add player
        addEntity(player);

        //Text
        textHealth = new Text(assets, this, 60, 5,
                Integer.toString(player.health), false);
        textHealth.x = textHealth.getBox().getWidth() / 2;
        textHealth.y = height - textHealth.getBox().getHeight() / 2;
        textHealth.addWaypoint(textHealth.x, textHealth.y + 10, 50);
        textHealth.addWaypoint(textHealth.x, textHealth.y - 10, 50);
        textHealth.addWaypoint(textHealth.x, textHealth.y + 10, 50);
        textHealth.addWaypoint(textHealth.x, textHealth.y, 50);

        textScore = new Text(assets, this, 60, 5,
                "0", false);

        textStart = new Text(assets, this, 80, 5,
                "START", true);
        textStart.x = width / 2.f;
        textStart.y = height - textStart.getBox().height * 2;
        textStart.addWaypoint(width / 2.f, height - textStart.getBox().height * 2,
                1500);
        textStart.addWaypoint(width / 2.f, height + textStart.getBox().height * 2,
                2000);
        textStart.start();

    }

    @Override
    public void show() {

    }

    public void addEntity(Entity entity) {
        if (entity instanceof Bullet)
            entities.add(0, entity);
        else
            entities.add(entity);

    }

    public void handleControls(float delta) {
        lastAccX = accX;
        accX = -Gdx.input.getAccelerometerX() * (abs(Gdx.input.getAccelerometerX()));
        smoothAccX = accX * 0.2f + lastAccX * (1.f - 0.2f);

        if (Gdx.input.justTouched()) {
            factory.createBullet(player.x, player.y, 90,
                    EntityFactory.BulletType.PlayerBase);
        }
    }

    public void logic(float delta) {

        //Player dead check
        if (end != 0.f && TimeUtils.timeSinceMillis(end) >= 2000.f) {
            game.setScreen(new ScoreScreen(game, assets, score));
        }

        if (player.isDead() && end == 0.f) {
            end = TimeUtils.millis();
        }


        //Step player and entities
        background.step(delta);
        textHealth.step(delta);
        textStart.step(delta);
        spawner.step(delta);

        if (!player.isDead()) {
            player.lerpX(smoothAccX / 10.f * center + center, delta);
            if (player.x < 0) player.x = 0;
            if (player.x > width) player.x = width;
        }

        ArrayList<Entity> c = new ArrayList<>(entities);
        for (Entity entity : c) {

            //If entity is dead, remove and skip
            if (entity.isDead()) {
                entities.remove(entity);
                continue;
            }

            //Step entities
            entity.step(delta);

            //Asteroid check
            //Skip if not asteroid
            if ((entity instanceof Asteroid) && entity.touches(player)) {
                player.damage(Asteroid.damage);
                entity.kill();
                textHealth.start();
                continue;
            }

            //Bullet checking
            //Skip if not bullet
            if (!(entity instanceof Bullet)) {
                continue;
            }
            Bullet bullet = (Bullet) entity;

            //Remove if outside screen
            if (!area.contains(bullet.getBox())) {
                bullet.kill();
                continue;
            }

            //Check if bullet touches anything
            for (Entity ship : c) {
                //Skip bullets touching bullets
                if (ship instanceof Bullet) {
                    continue;
                }

                //If not touching any ship
                if (!(ship instanceof Ship) || !bullet.touches(ship)) {
                    continue;
                }

                //Enemy
                if (ship instanceof Enemy && !bullet.enemy) {
                    ((Enemy) ship).damage(bullet.damage);
                    if (ship.isDead()) {
                        score += ((Enemy) ship).score;
                    }
                    bullet.damage(0);
                }
                //Player
                else if (ship instanceof Player && bullet.enemy) {
                    ((Ship) ship).damage(bullet.damage);
                    bullet.damage(0);
                    textHealth.start();
                }
            }
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(24.f / 255.f, 40.f / 255.f, 60.f / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        background.draw(batch);

        for (Entity e : entities) {
            e.draw(batch);
        }

        if (player.health < SpaceGame.playerHealth / 2) {
            textHealth.setBlink(true);
        } else {
            textHealth.setBlink(false);
        }

        textHealth.setText(String.format("%03d", Math.max(player.health, 0)));
        textHealth.draw(batch);

        textScore.setText(String.format("%07d", score));
        textScore.x = width - textScore.getBox().getWidth() / 2;
        textScore.y = height - textScore.getBox().getHeight() / 2;
        textScore.draw(batch);

        textStart.draw(batch);

        batch.end();
    }


    @Override
    public void render(float delta) {
        handleControls(delta);
        logic(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
