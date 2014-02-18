/**
 * 
 */
package net.obviam;

/**
 * @author impaler
 *
 */
public class RoundFiredState implements RifleState {

	private Rifle rifle;
	public RoundFiredState(Rifle rifle) {
		this.rifle = rifle;
	}

	@Override
	public void ejectClip() {
		System.out.println("!* You'll hurt yourself!");
	}

	@Override
	public void fireAmmo() {
		int count = 10;
		while (count > 0 && rifle.getAmmoCount() > 0) {
			System.out.print("> BANG! ");
			rifle.setAmmoCount(rifle.getAmmoCount() - 1);
			count--;
		}
		System.out.println();
		System.out.println("> Fired a round of " + (10 - count) + " bullets. Yeah!");
		if (rifle.getAmmoCount() <= 0) {
			rifle.setAmmoCount(0);
			rifle.setState(rifle.getOutOfAmmoState());
		} else {
			rifle.setState(rifle.getAutoFireState());
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
