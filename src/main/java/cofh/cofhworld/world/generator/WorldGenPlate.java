package cofh.cofhworld.world.generator;

import cofh.cofhworld.data.DataHolder;
import cofh.cofhworld.data.PlaneShape;
import cofh.cofhworld.data.block.Material;
import cofh.cofhworld.data.numbers.ConstantProvider;
import cofh.cofhworld.data.numbers.INumberProvider;
import cofh.cofhworld.util.random.WeightedBlock;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.IWorld;

import java.util.List;
import java.util.Random;

/**
 * @deprecated TODO: replace all booleans with ICondition; merge shape variables into a unified object
 */
@Deprecated
public class WorldGenPlate extends WorldGen {

	private final List<WeightedBlock> resource;
	private final Material[] material;

	private final INumberProvider radius;
	private INumberProvider height;

	private PlaneShape shape = PlaneShape.CIRCLE;
	private Rotation shapeRot = Rotation.NONE;
	private Mirror shapeMirror = Mirror.NONE;
	private boolean slim;

	public WorldGenPlate(List<WeightedBlock> resource, INumberProvider clusterSize, List<Material> materials) {

		this.resource = resource;
		radius = clusterSize;
		material = materials.toArray(new Material[0]);
		setHeight(1).setSlim(false);
	}

	@Override
	public boolean generate(IWorld world, Random rand, final DataHolder data) {

		int x = data.getPosition().getX();
		int y = data.getPosition().getY();
		int z = data.getPosition().getZ();

		final PlaneShape shape = this.shape;
		final Rotation rot = this.shapeRot;
		final Mirror mirror = this.shapeMirror;

		++y;
		int size = radius.intValue(world, rand, data);
		int height = this.height.intValue(world, rand, data.setValue("radius", radius));

		boolean r = false;
		for (int posX = x - size; posX <= x + size; ++posX) {
			int areaX = posX - x;
			for (int posZ = z - size; posZ <= z + size; ++posZ) {
				int areaZ = posZ - z;

				if (shape.inArea(areaX, areaZ, size, rot, mirror)) {
					for (int posY = y - height; slim ? posY < y + height : posY <= y + height; ++posY) {
						r |= generateBlock(world, rand, posX, posY, posZ, material, resource);
					}
				}
			}
		}

		return r;
	}

	public WorldGenPlate setSlim(boolean slim) {

		this.slim = slim;
		return this;
	}

	public WorldGenPlate setShape(PlaneShape shape, Rotation rot, Mirror mirror) {

		if (shape != null) {
			this.shape = shape;
		}
		if (rot != null) {
			this.shapeRot = rot;
		}
		if (mirror != null) {
			this.shapeMirror = mirror;
		}
		return this;
	}

	public WorldGenPlate setHeight(int height) {

		this.height = new ConstantProvider(height);
		return this;
	}

	public WorldGenPlate setHeight(INumberProvider height) {

		this.height = height;
		return this;
	}

}