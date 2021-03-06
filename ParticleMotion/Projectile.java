/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch03;
import java.awt.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;

/**
 * Projectile models the dynamics of a projectile and forms a template for other simulations.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  05/16/05
 */
public class Projectile implements Drawable, ODE {
  static final double g = 9.8;
  final static double alpha = 0.8; // d1 = 8
  final static double beta = 0.01; // d2 = 7
  private double B = 1;
  private double C = 1;
  double[] state = new double[4]; // { h, B, m, C }
  int pixRadius = 6;              // pixel radius for drawing of projectile
  ODESolver odeSolver = new RK4(this);

  public void setStepSize(double dt) {
    odeSolver.setStepSize(dt);
  }

  /**
   * Steps (advances) the time.
   *
   * @param dt the time step.
   */
  public void step() {
    odeSolver.step(); // do one time step using selected algorithm
  }

  /**
   * Sets the state.
   *
   * @param x
   * @param vy
   * @param m
   * @param dm
   */
  public void setState(double h, double b, double m) {
    state[0] = h; // h
    state[1] = b; // b
    state[2] = m; // m
    state[3] = 0; // time
  }

  /**
   * Gets the state.  Required for ODE interface.
   * @return double[] the state
   */
  public double[] getState() {
    return state;
  }

  /**
   * Gets the rate.  Required for ODE inteface
   * @param state double[] the state
   * @param rate double[]  the computed rate
   */
  public void getRate(double[] state, double[] rate) {
    rate[0] = -state[1];                              // rate of change of height
    rate[1] = g - C * Math.pow(state[2], alpha - 1)
                    * Math.pow(state[1], beta) - B
                    * Math.pow(state[1], 2);          // rate of change velocity
    rate[2] = B * state[2] * state[1];                // rate of change of mass
    rate[3] = 1;                                      // dt/dt = 1
  }
  
  public void setB(double B) {
	  this.B = B;
  }
  
  public void setC(double C) {
	  this.C = C;
  }

  /**
   * Draws the projectile. Required for Drawable interface.
   *
   * @param drawingPanel
   * @param g
   */
  public void draw(DrawingPanel drawingPanel, Graphics g) {
    int xpix = drawingPanel.xToPix(state[0]);
    int ypix = drawingPanel.yToPix(state[2]);
    g.setColor(Color.red);
    g.fillOval(xpix-pixRadius, ypix-pixRadius, 2*pixRadius, 2*pixRadius);
    g.setColor(Color.green);
    int xmin = drawingPanel.xToPix(-100);
    int xmax = drawingPanel.xToPix(100);
    int y0 = drawingPanel.yToPix(0);
    g.drawLine(xmin, y0, xmax, y0); // draw a line to represent the ground
  }
}

/* 
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.

 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must must also be be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
