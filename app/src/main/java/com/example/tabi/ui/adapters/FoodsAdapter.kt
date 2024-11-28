package com.example.tabi.ui.adapters
class FoodsAdapter(private val foods: List<String>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    override fun getItemCount(): Int = foods.size

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val foodName: TextView = view.findViewById(R.id.foodName)

        fun bind(food: String) {
            foodName.text = food
        }
    }
}