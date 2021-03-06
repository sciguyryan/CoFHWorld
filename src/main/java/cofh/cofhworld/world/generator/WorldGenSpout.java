package cofh.cofhworld.world.generator;

import cofh.cofhworld.data.PlaneShape;
import cofh.cofhworld.data.numbers.INumberProvider;
import cofh.cofhworld.util.random.WeightedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WorldGenSpout extends WorldGen {

	private final List<WeightedBlock> cluster;
	private final WeightedBlock[] genBlock;

	private final INumberProvider radius;
	private final INumberProvider height;

	private PlaneShape shape = PlaneShape.CIRCLE;

	public WorldGenSpout(List<WeightedBlock> resource, List<WeightedBlock> material, INumberProvider radius, INumberProvider height) {

		cluster = resource;
		this.radius = radius;
		this.height = height;
		genBlock = material.toArray(new WeightedBlock[material.size()]);
	}

	public WorldGenSpout setShape(PlaneShape shape) {

		this.shape = shape;
		return this;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		int xCenter = pos.getX();
		int yCenter = pos.getY();
		int zCenter = pos.getZ();

		INumberProvider.DataHolder data = new INumberProvider.DataHolder(pos);

		int height = this.height.intValue(world, rand, data);
		boolean r = false;
		for (int y = 0; y < height; ++y) {
			int radius = this.radius.intValue(world, rand, data.setPosition(pos.add(0, y, 0)));
			for (int x = -radius; x <= radius; ++x) {
				for (int z = -radius; z <= radius; ++z) {
					if (shape.inArea(x, z, radius)) {
						r |= generateBlock(world, rand, xCenter + x, yCenter + y, zCenter + z, genBlock, cluster);
					}
				}
			}
		}

		return r;
	}

}
