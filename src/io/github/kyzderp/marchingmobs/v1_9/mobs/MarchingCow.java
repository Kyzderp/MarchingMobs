package io.github.kyzderp.marchingmobs.v1_9.mobs;

import io.github.kyzderp.marchingmobs.IMarcher;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityCow;
import net.minecraft.server.v1_9_R1.SoundEffect;
import net.minecraft.server.v1_9_R1.World;


public class MarchingCow extends EntityCow implements IMarcher 
{
	private boolean isMarcher;
	private boolean isMarchRideable;
	private float fixedYaw;
	private float fixedPitch;
	private float fixedSpeed;

	public MarchingCow(World world)
	{
		super(world);
		this.isMarcher = false;
		this.isMarchRideable = false;
		this.fixedYaw = 0F;
		this.fixedPitch = 0F;
		this.fixedSpeed = 0F;
	}

	@Override
	public void g(float side, float forward)
	{
		if (!this.isMarcher)
			super.g(side, forward);
		else
		{
			this.i(this.fixedSpeed); // Speed

			this.lastYaw = this.yaw = this.fixedYaw; // Turn
			this.pitch = this.fixedPitch; // Up or down
			this.setYawPitch(this.fixedYaw, this.fixedPitch);
			this.aO = this.aM = this.yaw;

			this.setFlag(7, true); // Sets it to be always walking

			super.g(0, 1);
		}
	}

	@Override
	public void setOnFire(int i)
	{
		if (this.isMarcher)
			this.extinguish();
	}

	/**
	 * This only works between-mobs. See g() for with-player.
	 */
	@Override
	public void collide(Entity entity)
	{
		if (this.isMarcher)
			return;
		super.collide(entity);
	}

	/**
	 * Makes collisions with players not push the cow
	 * Result of this is the cow pushes the player.
	 */
	@Override
	public void g(double d0, double d1, double d2)
	{
		if (this.isMarcher)
			return;
		super.g(d0, d1, d2);
	}

	@Override
	protected SoundEffect bS()
	{
		if (this.isMarcher)
			return null;
		return super.bS();
	}

	public void setMarcher(float yaw, float pitch, float speed)
	{
		this.fixedYaw = yaw;
		this.fixedPitch = pitch;
		this.fixedSpeed = speed;
		this.isMarcher = true;
		this.fireProof = true;
	}

	public void setMarchRideable(boolean rideable)
	{
		this.isMarchRideable = rideable;
	}

	public boolean getMarchRideable()
	{
		return this.isMarchRideable;
	}

	public boolean getMarcher()
	{
		return this.isMarcher;
	}
}
