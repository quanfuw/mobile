/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class AmmoFiredState implements RifleState {

	private Rifle rifle;
	public AmmoFiredState(Rifle rifle) {
		this.rifle = rifle;
	}

	@Override
	public void ejectClip() {
		System.out.println("!* You'll hurt yourself!");
	}

	@Override
	public void fireAmmo() {
		rifle.setAmmoCount(rifle.getAmmoCount() - 1);
		System.out.println("> Fired 1 bullet.");
		if (rifle.getAmmoCount() == 0) {
			rifle.setState(rifle.getOutOfAmmoState());
		} else {
			rifle.setState(rifle.getManualFireState());
		}
	}

	@Override
	public void insertClip() {
		System.out.println("!* Clip is already present...and you're firing!");
	}

	@Override
	public void pullTrigger() {
		System.out.println("!* You are jamming the gun!");
	}

	@Override
	public void switchManualAuto() {
		System.out.println("!* Still firing!");
	}

}
