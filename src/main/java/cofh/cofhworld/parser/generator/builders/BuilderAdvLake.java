package cofh.cofhworld.parser.generator.builders;

import cofh.cofhworld.data.block.Material;
import cofh.cofhworld.data.block.MaterialPropertyMaterial;
import cofh.cofhworld.data.condition.ICondition;
import cofh.cofhworld.data.condition.operation.BinaryCondition;
import cofh.cofhworld.data.condition.operation.ComparisonCondition;
import cofh.cofhworld.data.condition.random.RandomCondition;
import cofh.cofhworld.data.condition.world.MaterialCondition;
import cofh.cofhworld.data.numbers.data.DataProvider;
import cofh.cofhworld.parser.generator.builders.base.BaseBuilder;
import cofh.cofhworld.util.random.WeightedBlock;
import cofh.cofhworld.world.generator.WorldGenAdvLakes;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class BuilderAdvLake extends BaseBuilder<WorldGenAdvLakes> {

	private static final List<WeightedBlock> GAP_BLOCK = Collections.singletonList(WeightedBlock.AIR_NORM);
	private static final ICondition OUTLINE = new BinaryCondition(
			new BinaryCondition(
			new ComparisonCondition(
			new DataProvider("layer"),
							new DataProvider("fill-height"),
							"LESS_THAN"),
									new RandomCondition(),
					"AND"),
							new MaterialCondition(Collections.singletonList(new MaterialPropertyMaterial(true, "SOLID"))),
			"AND");

	private List<WeightedBlock> filler = GAP_BLOCK;

	private List<WeightedBlock> outline = null;
	private ICondition outlineCondition = OUTLINE;

	public BuilderAdvLake(List<WeightedBlock> resource, List<Material> material) {

		super(resource, material);
	}

	public void setOutlineCondition(ICondition outline) {

		this.outlineCondition = outline;
	}

	public void setOutline(List<WeightedBlock> blocks) {

		this.outline = blocks;
	}

	public void setFiller(List<WeightedBlock> blocks) {

		this.filler = blocks;
	}

	@Nonnull
	@Override
	public WorldGenAdvLakes build() {

		return new WorldGenAdvLakes(resource, material, filler, outline, outlineCondition);
	}
}
