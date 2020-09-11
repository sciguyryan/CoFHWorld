package cofh.cofhworld.data.condition.random;

import cofh.cofhworld.data.DataHolder;
import cofh.cofhworld.data.condition.ICondition;
import net.minecraft.world.World;

import java.util.Random;

public class RandomCondition implements ICondition {

	@Override
	public boolean checkCondition(World world, Random rand, DataHolder data) {

		return rand.nextBoolean();
	}
}
