# Predicates 

one_node

	[min-instances(num)]
	[max-instances(num)]
	[singleton] = [min-instance(1)] && [max-instances(1)]
	[value-type]
		[integer-value]
		[float-value]
		[bool-value]
		[string-value]
		[enum-value(name*)]

one_edge

	[source-min-multiplicity(num)]
	[source-max-multiplicity(num)]
	[target-min-multiplicity(num)]
	[target-max-multiplicity(num)]
	[surjective] = [source-min-multiplicity(1)]
	[injective] = [source-max-multiplicity(1)]
	[total] = [target-min-multiplicity(1)]
	[right-unique aka function] = [target-max-multiplicity(1)]
	[set]
	[ordered]
	[containment]

loop

	[acyclic]

2-cell

	[=]
	[includedIn]

1-cycle

	[inverse]

n-span
	
	[xor]
	[and]
	[or]
	[forall]


# Operations

<proxy>
<provy_inv>
<invert>
<apply_attribute_op>
<compose>
<compose_inv>
<reflexive_closure>
<transitive_closure>
<refl_trans_closure>
<insert_node>
<delete_node>
<insert_edge>
<delete_edge>
<sum_edges>
<subtract_edges>

<product>
<coproduct>
<pushout>
<pullback>
<equalizer>
<coequalizer>


