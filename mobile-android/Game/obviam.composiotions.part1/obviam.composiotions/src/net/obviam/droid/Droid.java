/**
 * 
 */
package net.obviam.droid;

import net.obviam.chassis.Chassis;
import net.obviam.math.Vector2f;
import net.obviam.weapon.Weapon;

/**
 * @author impaler
 *
 */
public abstract class Droid {

	protected static int nextId	= 0;	// the next available ID

	protected String 	id;			// unique id
	protected Vector2f 	position;	// the droid's position on the map
	protected Weapon 	weapon;		// the weapon which will be used in fights
	protected Chassis	chassis;	// the chassis on which the droid is placed

	// the unique ID of the droid in the game
	public String getId() {
		return id;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Chassis getChassis() {
		return chassis;
	}
	public void setChassis(Chassis chassis) {
		this.chassis = chassis;
	}
	
	public void moveToPosition(int x, int y) {
		System.out.print(id + " > " );
		chassis.moveTo(x, y);
	}
	
	/**
	 *  Engages the position on the screen whether it is occupied by
	 *  an enemy or not. Each strategy should decide how to do it. 
	 */ 
	public void attackPosition(int x, int y) {
		System.out.print(id + " > ");
		weapon.useWeapon(new Vector2f(x, y));
	}
	
	/**
	 * Displays some info on the droid
	 */
	public abstract void display();
	
}
