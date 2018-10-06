package prithvi.io.workmanager.utility.extentions

import android.support.v7.util.DiffUtil
import prithvi.io.workmanager.utility.Identifiable

class DiffUtilCallback(
        private val oldSize: () -> Int,
        private val newSize: () -> Int,
        private val areItemsSame: (Int, Int) -> Boolean,
        private val areContentsSame: (Int, Int) -> Boolean
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldSize()
    override fun getNewListSize() = newSize()
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = areItemsSame(oldItemPosition, newItemPosition)
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = areContentsSame(oldItemPosition, newItemPosition)
}

fun dispatchUpdatesWithDiff(
        oldSize: () -> Int,
        newSize: () -> Int,
        areItemsSame: (Int, Int) -> Boolean,
        areContentsSame: (Int, Int) -> Boolean
): DiffUtil.DiffResult {
    return DiffUtil.calculateDiff(DiffUtilCallback(oldSize, newSize, areItemsSame, areContentsSame))
}

fun <T : Identifiable> dispatchListDiff(oldList: List<T>, newList: List<T>) = dispatchUpdatesWithDiff(
        oldSize = { oldList.size },
        newSize = { newList.size },
        areItemsSame = { oldPos, newPos -> oldList[oldPos].identifier == newList[newPos].identifier },
        areContentsSame = { oldPos, newPos -> oldList[oldPos] == newList[newPos] }
)