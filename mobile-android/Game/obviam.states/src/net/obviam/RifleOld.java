/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class RifleOld {

	// defining the states
	final static int NO_CLIP		= 0;
	final static int HAS_CLIP		= 1;
	final static int AMMO_FIRED		= 2;
	final static int OUT_OF_AMMO	= 3;
	
	int state = NO_CLIP;	// instance variable holding the current state
	int ammoCount = 0;		// we count the ammo

	// to be used in toString()
	static String[] states = new String[] { 
			"Empty Magazine (No Clip)", 
			"Ready (Has Clip)",
			"Firing", 
			"Out of Ammo" }; 

	// **********************
	// Creating the actions
	// **********************

	public void insertClip() {
		// We check each possible state and act according to them
		if (state == HAS_CLIP) {
			System.out.println("!* There is already a clip loaded.");
		} else if (state == AMMO_FIRED) {
			System.out.println("!* You'll hurt yourself!!!");
		} else if (state == OUT_OF_AMMO) {
			System.out.println("!* You need to take out the empty clip first.");
		} else if (state == NO_CLIP) {
			state = HAS_CLIP;
			ammoCount = 3;
			System.out.println("> You have loaded a clip with " + ammoCount + " bulletts.");
		}
	}

	public void ejectClip() {
		if (state == NO_CLIP) {
			System.out.println("!* The magazine is empty.");
		} else if (state == AMMO_FIRED) {
			System.out.println("!* You'll hurt yourself!!!");
		} else if (state == HAS_CLIP) {
			// You could still eject it if you want but for the sake of
			// simplicity let's use up the ammo first
			System.out.println("!* Use up all your ammo first.");
		} else if (state == OUT_OF_AMMO) {
			state = NO_CLIP;
			System.out.println("> You have unloaded a clip.");
		}
	}

	public void pullTrigger() {
		if (state == NO_CLIP) {
			System.out.println("!* Empty Click! - No clip!");
		} else if (state == AMMO_FIRED) {
			System.out.println("!* Jammed! - Is being fired!");
		} else if (state == OUT_OF_AMMO) {
			System.out.println("!* Click! Out of ammo.");
		} else if (state == HAS_CLIP) {
			System.out.println("> BANG!!!");
			state = AMMO_FIRED;
			fireAmmo();
		}
	}

	public void fireAmmo() {
		if (state == NO_CLIP) {
			System.out.println("!* Empty magazine.");
		} else if (state == HAS_CLIP) {
			System.out.println("!* Need to pull the trigger first!");
		} else if (state == OUT_OF_AMMO) {
			System.out.println("!* Out of ammo.");
		} else if (state == AMMO_FIRED) {
			ammoCount--;
			System.out.println("> Bullet on its way!");
			// we check if the clip is empty
			if (ammoCount == 0) {
				// yes, it's empty
				System.out.println("> Darn! Out of ammo");
				state = OUT_OF_AMMO;
			} else {
				state = HAS_CLIP;
			}
		}
	}

	@Override
	public String toString() {
		return "<< RIFLE [state=" + states[state] + ", ammo=" + ammoCount + "] >>";
	}
	
}
