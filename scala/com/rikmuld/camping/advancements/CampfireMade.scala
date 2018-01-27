package com.rikmuld.camping.advancements

import com.google.gson.{JsonDeserializationContext, JsonObject}
import com.rikmuld.camping.Lib.AdvancementInfo.CAMPFIRES_MADE
import com.rikmuld.camping.Lib.NBTInfo
import com.rikmuld.corerm.advancements.{AdvancementTrigger, TriggerInstance}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ResourceLocation

object CampfireMade {
  class Trigger extends AdvancementTrigger[Int, Instance] {
    protected val id: ResourceLocation =
      CAMPFIRES_MADE

    override def deserializeInstance(json: JsonObject, context: JsonDeserializationContext): Instance ={
      val campfire = Option(json.get("campfire")) map (_.getAsInt)
      val total = Option(json.get("total")) map (_.getAsJsonArray) map(array =>
        for(i <- 0 until array.size)
          yield array.get(i).getAsInt
      )

      new Instance(campfire, total)
    }
  }

  protected class Instance(campfire: Option[Int],
                           total: Option[Seq[Int]]) extends TriggerInstance[Int](CAMPFIRES_MADE) {

    def test(player: EntityPlayerMP, damage: Int): Boolean = {
      val dataTag = player.getEntityData.getCompoundTag(NBTInfo.ACHIEVEMENTS)
      var madeArray = dataTag.getIntArray("campfires")

      if(!madeArray.contains(damage))
        madeArray = madeArray :+ damage

      dataTag.setIntArray("campfires", madeArray)
      player.getEntityData.setTag(NBTInfo.ACHIEVEMENTS, dataTag)

      campfire.fold(true)(_ == damage) &&
        total.fold(true)(_.forall(i => madeArray.contains(i)))
    }
  }
}
