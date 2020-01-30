package com.example.myapplication.recyclerview.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Machin
import kotlinx.android.synthetic.main.item_machin.view.*

class MachinAdapter(private val myDataset: MutableList<Machin>)  : RecyclerView.Adapter<MachinAdapter.MachinViewHolder>() {

    // Le MachinViewHolder prend en paramètre une View. Vous pourriez spécifier quelle type de vue (LinearLayout,
    // ConstraintLayout) mais ça impose une contrainte. Si demain on décide d'arrêter les ConstraintLayout ici ça plantera.
    // Ça peut être un effet voulu. Mais ici on ne va pas compliquer les choses. Quoi qu'il en soit, Ce ViewHolder
    // contient la vue. Cette vue c'est votre item, vous l'avez rédigé dans votre res>layout et il commence par item_machin
    // vous savez donc très bien de quelles vues enfant il est composé.
    // Le mien est composé de : 1 image et 2 zones de texte. Donc le les instancie ici.

    class MachinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ici j'utilise l'id de la vue enfant, ATTENTION donc à la nommer de manière non ambigue.
        // C'est une spécificité kotlin. En Java on sera obligé de faire "findiviewById(R.layout.item_machin.item_machin_label)
        // et le caster avec le type de vue qui correspond.
        val _label = itemView.item_machin_label
        val _desc = itemView.item_machin_desc
        val _avatar = itemView.item_machin_avatar
    }

    // difficile de dire lequel vient avant l'autre, mais ici c'est votre première fonction de l'adapter à surcharger.
    // Elle prend un groupe de vue, le parent (et le viewType que dont je ne parlerai pas ici). Ici dans tous vos projets,
    // vous avez juste à changer le nom de l'item R.layout.item_votre_item.
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MachinAdapter.MachinViewHolder {
        // create a new view
        var itemView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_machin, parent, false)
                    as View
        return MachinViewHolder(itemView) // Dans le return vous construisez un ViewHolder tel que vous l'avez défini au  dessus.
    }

    // Ici vous voyez que sont passés en paramètress votre viewHolder et une position. Cette position c'est celle de
    // votre liste de donnée et de sa position correspondante dans le recyclerView. A partir de là, vous laissez faire
    // le boulot au RecyclerView. Mais pour qu'il le fasse vous devez lui dire ce qu'il doit faire à chaque fois.
    // Attention aux opérations longues ! Ce n'est pas ici qu'il faut les faire !
    override fun onBindViewHolder(holder: MachinViewHolder, position: Int) {
        holder._label.text = myDataset[position].label
        holder._desc.text = myDataset[position].description
    }

    // Ici vous retournez juste un moyen de compter le nombre d'éléments constituants la liste. Le plus souvent c'est size
    override fun getItemCount() = myDataset.size
}
// Maintenant qu'on a fait ça, on va déclarer un RecyclerView dans un UI controller (Activity ou Fragment) et lui passer les données.
