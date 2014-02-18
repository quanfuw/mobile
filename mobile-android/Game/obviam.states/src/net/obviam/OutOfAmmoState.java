/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class OutOfAmmoState implements RifleState {

	private Rifle rifle;
	public OutOfAmmoState(Rifle rifle) {
		this.rifle = rifle;
	}
	
	@Override
	public void ejectClip() {
		rifle.setState(rifle.getEmptyState());
		System.out.println("> Clip ejected.");
	}

	@Override
	public void fireAmmo() {
		System.out.println("!* You can't fire with no ammo.");
	}

	@Override
	public void insertClip() {
		System.out.println("!* There is an empty clip inserted already!");
	}

	@Override
	public void pullTrigger() {
		System.out.println("!* Out of ammo!");
	}

	@Override
	public void switchManualAuto() {
		System.out.println("!* Plesea reload first");
	}

}
