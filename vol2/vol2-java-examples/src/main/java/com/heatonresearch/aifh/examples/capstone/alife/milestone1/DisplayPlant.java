package com.heatonresearch.aifh.examples.capstone.alife.milestone1;

import javax.swing.*;
import java.awt.*;

/**
 * A panel used to display a plant. This panel will be used in all milestones.
 */
public class DisplayPlant extends JPanel {
    /**
     * The universe that we will render from.
     */
    private PlantUniverse universe;

    /**
     * The most green color that a plant can take on.
     */
    private Color FULL_GREEN = new Color(0, 255, 0);

    /**
     * The most brown color that a plant can take on.
     */
    private Color FULL_BROWN = new Color(165, 42, 42);

    /**
     * The color of the sky.
     */
    private Color SKY_COLOR = new Color(135, 206, 250);

    /**
     * The color of the dirt.
     */
    private Color DIRT_COLOR = new Color(96, 96, 96);

    /**
     * A gradient of colors between full green and brown.
     */
    private Color[] gradient;

    /**
     * The best score, to display.
     */
    private double bestScore;

    /**
     * The current generation, for display.
     */
    private int generation = 0;


    /**
     * The constructor builds the gradient.
     */
    public DisplayPlant() {
        int gradentRangeRed = FULL_GREEN.getRed() - FULL_BROWN.getRed();
        int gradentRangeGreen = FULL_GREEN.getGreen() - FULL_BROWN.getGreen();
        int gradentRangeBlue = FULL_GREEN.getBlue() - FULL_BROWN.getBlue();

        int maxRange = Math.max(Math.max(
                Math.abs(gradentRangeRed), Math.abs(gradentRangeGreen)), Math.abs(gradentRangeBlue));

        double scaleRed = (double) gradentRangeRed / (double) maxRange;
        double scaleGreen = (double) gradentRangeGreen / (double) maxRange;
        double scaleBlue = (double) gradentRangeBlue / (double) maxRange;

        this.gradient = new Color[maxRange];

        for (int i = 0; i < maxRange; i++) {
            gradient[i] = new Color(
                    (int) (FULL_BROWN.getRed() + (i * scaleRed)),
                    (int) (FULL_BROWN.getGreen() + (i * scaleGreen)),
                    (int) (FULL_BROWN.getBlue() + (i * scaleBlue)));
        }
    }

    /**
     * Paint the plant.
     * @param g The graphics object.
     */
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        // Universe always has a fixed number of cells.  Determine the on-screen dimensions of a cell.
        int cellWidth = Math.max(width / PlantUniverse.UNIVERSE_WIDTH, 1);
        int cellHeight = Math.max(height / PlantUniverse.UNIVERSE_HEIGHT, 1);

        // Loop over all cells.
        for (int row = 0; row < PlantUniverse.UNIVERSE_HEIGHT; row++) {
            for (int col = 0; col < PlantUniverse.UNIVERSE_WIDTH; col++) {
                PlantUniverseCell cell = this.getUniverse().getCell(row,col);

                // For empty cells display either the ground or sky color.
                // Roots are always white.
                if (row >= PlantUniverse.GROUND_LINE) {
                    if (cell.isAlive()) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(DIRT_COLOR);
                    }
                } else {
                    if (cell.isAlive()) {
                        int idx = (int)((this.gradient.length-1) * cell.getLeafyness());
                        g.setColor(this.gradient[idx]);
                    } else {
                        g.setColor(SKY_COLOR);
                    }

                }


                g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);

            }
        }

        // Display the stats at the bottom.
        if( generation>0 ) {
            int y = cellHeight * PlantUniverse.UNIVERSE_HEIGHT;
            g.setColor(Color.white);
            g.fillRect(0,y,height,width);
            FontMetrics fm = g.getFontMetrics();
            y+=fm.getHeight();
            g.setColor(Color.black);
            g.drawString("Generation: " + this.generation,0,y);
            y+=fm.getHeight();
            g.drawString("Best Score:  " + this.bestScore,0,y);
        }


    }

    /**
     * @return The universe.
     */
    public PlantUniverse getUniverse() {
        return universe;
    }

    /**
     * Set the universe to display.
     * @param universe The universe to display.
     */
    public void setUniverse(final PlantUniverse universe) {
        this.universe = universe;
    }

    /**
     * @return The best score.
     */
    public double getBestScore() {
        return bestScore;
    }

    /**
     * Set the best score.
     * @param bestScore
     */
    public void setBestScore(final double bestScore) {
        this.bestScore = bestScore;
    }

    /**
     * @return The generation.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Set the current generation.
     * @param generation The generation.
     */
    public void setGeneration(final int generation) {
        this.generation = generation;
    }
}