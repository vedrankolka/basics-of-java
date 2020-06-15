package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * A class that represents the current state of the turtle used for drawing on the screen.
 * @author Vedran Kolka
 */
public class TurtleState {

	/**
	 * The position vector of the turtle.
	 */
	public Vector2D position;
	/**
	 * The direction vector of the turtle.
	 */
	public Vector2D direction;
	/**
	 * The current color in which the turtle is drawing.
	 */
	public Color color;
	/**
	 * The length of the turtle's position shift.
	 */
	public double delta;
	
	public TurtleState(Vector2D position, Vector2D direction, Color color, double delta) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.delta = delta;
	}

	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, delta);
	}
	
	
	
}
