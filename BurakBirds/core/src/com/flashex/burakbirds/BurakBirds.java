package com.flashex.burakbirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class BurakBirds extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Texture bird;
    Texture beeAngry1;
    Texture beeAngry2;
    Texture beeAngry3;
    float birdX = 0, birdY = 0;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.1f;

    int numberOfEnemies = 4;
    float[] enemyX = new float[numberOfEnemies];
    float distance = 0;
    float enemyVelocity = 2;
    float[] enemyOffset1 = new float[numberOfEnemies];
    float[] enemyOffset2 = new float[numberOfEnemies];
    float[] enemyOffset3 = new float[numberOfEnemies];
    Random random;
    Circle birdCircle;
    Circle[] enemyCircle1;
    Circle[] enemyCircle2;
    Circle[] enemyCircle3;
    int score = 0;
    int scoreEnemy = 0;
    BitmapFont font;
    BitmapFont theEnd;


    @Override
    public void create() {
        //open the game creat func
        batch = new SpriteBatch();
        random = new Random();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4);

        theEnd = new BitmapFont();
        theEnd.setColor(Color.WHITE);
        theEnd.getData().setScale(4);
        //char create
        img = new Texture("background.png");
        bird = new Texture("gamachar.png");
        beeAngry1 = new Texture("enemy.png");
        beeAngry2 = new Texture("enemy.png");
        beeAngry3 = new Texture("enemy.png");
        distance = Gdx.graphics.getWidth() / 2;

        birdX = Gdx.graphics.getWidth() / 3;
        birdY = Gdx.graphics.getHeight() / 3;

        //wrap
        birdCircle = new Circle();
        enemyCircle1 = new Circle[numberOfEnemies];
        enemyCircle2 = new Circle[numberOfEnemies];
        enemyCircle3 = new Circle[numberOfEnemies];

        //if enemy loop
        for (int i = 0; i < numberOfEnemies; i++) {
            enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            enemyX[i] = Gdx.graphics.getHeight() - beeAngry1.getWidth() / 2 + i * distance;

            enemyCircle1[i] = new Circle();
            enemyCircle2[i] = new Circle();
            enemyCircle3[i] = new Circle();
        }
    }

    @Override
    public void render() {
        //resume game

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            if (enemyX[scoreEnemy] < Gdx.graphics.getWidth()/2-bird.getHeight()/2   ) {
                score++;
                if (scoreEnemy < numberOfEnemies - 1) {
                    scoreEnemy++;
                } else {
                    scoreEnemy = 0;
                }
            }
            if (Gdx.input.justTouched()) {
                //touching the screen
                velocity = velocity - 7;
            }
            for (int i = 0; i < numberOfEnemies; i++) {

                if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;

                    enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }


                batch.draw(beeAngry1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset1[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(beeAngry2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(beeAngry3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

                enemyCircle1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset1[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getWidth() / 30);
                enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getWidth() / 30);
                enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getWidth() / 30);
            }


            if (birdY > 0) {
                velocity = velocity + gravity;
                birdY = birdY - velocity;
            } else {
                gameState = 2;
            }
        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            theEnd.draw(batch, "Game Over! Tap To Play Again.", Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                birdY = Gdx.graphics.getHeight() / 3;

                //if enemy loop
                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    enemyX[i] = Gdx.graphics.getHeight() - beeAngry1.getWidth() / 2 + i * distance;

                    enemyCircle1[i] = new Circle();
                    enemyCircle2[i] = new Circle();
                    enemyCircle3[i] = new Circle();
                }
                velocity = 0;
                score = 0;
                scoreEnemy = 0;
            }
        }
        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
        font.draw(batch, String.valueOf(score), 100, 100);
        batch.end();
        birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

        for (int i = 0; i < numberOfEnemies; i++) {
            if (Intersector.overlaps(birdCircle, enemyCircle1[i]) || Intersector.overlaps(birdCircle, enemyCircle2[i]) || Intersector.overlaps(birdCircle, enemyCircle3[i])) {
                gameState = 2;
            }
        }

    }

    @Override
    public void dispose() {
        //game the close
        batch.dispose();
        img.dispose();
    }
}
