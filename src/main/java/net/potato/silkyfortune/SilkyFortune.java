package net.potato.silkyfortune;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.enchantment.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.*;
import net.minecraft.util.Identifier;
import org.slf4j.*;

import java.util.*;

import static net.minecraft.block.Blocks.*;

public class SilkyFortune implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("silkyfortune");
	public static final Block[] ORES = {
			COAL_ORE, DEEPSLATE_COAL_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE, DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE, GOLD_ORE,
			DEEPSLATE_GOLD_ORE, NETHER_GOLD_ORE, IRON_ORE, DEEPSLATE_IRON_ORE, LAPIS_ORE, DEEPSLATE_LAPIS_ORE, NETHER_QUARTZ_ORE, REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE
	};
	public static ArrayList<Identifier> ar = new ArrayList<>();
	@Override
	public void onInitialize() {
		for(Block blk: ORES){
			ar.add(blk.getLootTableId());
		}
		LOGGER.info("Hello fellow human being (if you see this)! This mod was coded by me, a potato :D! (yes don't question how potatoes can code they can)");
		for(int i = 0; i < ar.size(); i++){
			Identifier id = ar.get(i);
			int finalI = i;
			LootTableEvents.MODIFY.register((resourceManager, lootManager, id1, tableBuilder, source) -> {
				if(source.isBuiltin() && id.equals(id1)){
					LootPool.Builder pb = LootPool.builder()
							.rolls(ConstantLootNumberProvider.create(1))
							.with(ItemEntry.builder(ORES[finalI]))
							.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.ANY))).build())
							.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.FORTUNE, NumberRange.IntRange.ANY))))
							.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
							.apply(ExplosionDecayLootFunction.builder());
					tableBuilder.pool(pb);
				}
			});
		}
	}
}
