package com.rikmuld.camping.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{IInventory, InventoryCrafting, SlotCrafting}

class SlotTabbedCrafting(player: EntityPlayer, craftInv: InventoryCrafting, inv: IInventory, index: Int, x:Int, y:Int, val tab: Int) extends
  SlotCrafting(player, craftInv, inv, index, x, y) with SlotTabbed {
}