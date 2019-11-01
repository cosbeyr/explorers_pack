
**Solar**

There are [Solar USB chargers](SolarUSBCharger). Harvesting the power of the sun is a great and very clean way to generate power. There are only 2 real drawbacks for this kind of power:

* it is not available at night
* the output depends on the weather

However, if it is available you can use it effortlessly. While you are harnessing the power of the sun you can engage in other actions such as obtaining food or building a shelter.

**Bicycle**

In contrast to the sun, generating power with your Bicycle is more effort but it can also be a lot of fun and serve as good exercise. And it might be easily available as most bikes already have a "generator" installed. Usually this "generator" / dynamo is there to power your lights on the bike. However, it can also charge a phone. You only need a converter to 5V/USB - you can build one yourself or buy one. Be careful here though - there are some really bad converters out there. I would recommend building one yourself.

**Improvised Generator**

You can use almost any DC motor to generate electricity. All you must do is turn the motor by hand or by other means and electricity will be generated at the (+) and (-) leads of the motor. (The electricity produced will be in the form of AC, so a diode/rectifier is required to make use of the generated electricity to charge a battery).

The faster the motor turns, the higher the voltage produced will be. The current produced will depends on the internals of the motor. Generally the bigger and more difficult the motor is the turn, the more current it will produce.

Motors that do not work for this are most fan motors, and motors that do not have magnets in them. If the motor is magnetic without being plugged in, chances are it will work.

Microwave oven turntable motors (the one that turns the glass plate on the bottom) can easily produce 300-400 volts when turned by hand.

**Wind**

Wind-power is also great. It can be available day and night - but you need wind. Also, generators are often bulkier and hard to transport. However, it can be a great option if you are not trying to move to anywhere. It is not particularly  difficult to build a wind-generator. Basically, you just need to get the wind to turn something - use junk and your [MultiTool](MultiTool) to get the wind to rotate a shaft and attach a motor. A motor is basically the same as a generator - you just need to build some electronics to harvest the power.

Scottish engineer Hugh Piggott inspired since year 2000, ONG in several countries (US, France, Nicaragua) building low-tech nearly hand made wind-turbine from 1.2m to 4.2m, and 200W to 2000W. He wrote many books about it, available from his [website](http://scoraigwind.co.uk/) and translated by the motivated ONG.

**Water**

You can also use water to generate power. One option is to use the flow of a river passing by. There you basically do the same as you would with the wind-turbine - in this case, water rather than wind turns the generator. One big advantage of using a river is that you get a steady rotation-speed, and the flow usually does not stop, while the wind might. However, it depends on having access to a river or a dam. Speaking of a dam - you can also build things to direct the water and make the flow faster.

**Heat-Difference**

You can also generate power by harnessing heat differentials, e.g. with a fire. There are now turnkey solutions like the [BioLite Wood Burning Campstove with USB](CampStoveUSB) or there are also some fire pots out there with a power USB outlet. Breakthroughs in science and engineering have recently introduced new exciting, portable options. You can also harvest the power of a fire via steam, as is often done in a large scale today. But on a small scale, this is labor-intensive to use and hard to implement in practice.

**Hand Crank**

Using the muscles of your arm is another option. There are small USB cranks to operate by hand, some even [with a light](HandCrankUSB) or which also recharge standard AA/LR06 batteries. But be aware that this really binds you to the task, as you cannot do much else while turning the crank. Additionally, I personally find it an exhausting and boring task - I prefer to use the bicycle (which is using bigger muscles). However, it is always good to have an array of options and I own a small crank for emergencies.

**Car**

You can also use a car or truck with a combustion engine to generate power. Cars and trucks (hereafter referred to as "car") always come with a generator in the form of an alternator that recharges the car's battery and provides power to the car's systems while the engine is running. When the engine is running, power is being produced. Most car batteries are 12V. You just have to convert the 12V to 5V for the phone. You can really easily build this yourself cheaply e.g. with a LM7805 Voltage Regulator IC or you buy a [car usb-charger](CarUSBCharger). But be aware that this is a wildly inefficient means to produce power, as the majority of the output is being used to power the car itself. You also hurt the environment when doing so, as the vehicle is being powered by gasoline or diesel fuel. This should be the last resort as a car is making 80% of heat energy (usually from fossil fuel) using just 20% to actually move you around, and an accessory converts a small portion of this movement into electricity via average efficiency converters. It is much better to take advantage of this while driving to your destination, as the power is there anyway.

**Voltaic pile**

Alessandro Volta was an Italian physicist, chemist and a pioneer of electricity and power. He is the credited inventor of the first electrical battery and invented the Voltaic pile in 1799. Early experiments were conducted in an easy to reproduce method : plug two pieces of metal into a lemon.

If the pieces of metal are made of copper for one (like electric wires or cents, the brown/orange shining ones) and zinc for the other (like a paper-clip in a galvanized steel - covered by zinc - or old bars metal-sheet cover, gutters and Paris roof covers), both approximately 3 centimeters squared, plugged at another 3cm of distance from one another, a tension of around 1V will be generated between them. The copper part will be the positive pole of this power generator, and the zinc one the negative pole.

To recharge a USB powered device you will need a pile of 5 lemon cells plugged in series (negative pole of the 1st to the positive pole of the 2nd, etc).

This method won't provide a lot of energy, so you'll need a lot of lemons (cut in four lengths and sliced for instance). The good news is that a lot of other fruits (oranges, grapefruits), vegetables (potatoes, plantain pith) or acid liquids (vinegar, grape juice, salt water) can be used as the acidic electrolyte for the reaction. They just need to be acid, and even a non-acid-tasting material can have a compliant pH, so being acid enough to produce electricity. All the given examples end up producing ~1V cells. Fruit is convenient because it provides both the electrolyte and a simple way to support the electrodes (metallic parts).

A pile of 5 lemon cells will deliver 5V and around 1mA. The bigger the electrodes, the bigger the current. For instance : 5cm by 5cm electrodes in 10cl of 8% acidic vinegar with 5g of salt will deliver pikes of 40mA slowly reducing to 10mA (in 5 minutes). A one meter long gutter, filled of sea water, with a one meter long plumbing copper hose in it, separated by a paper or fabrics layer should produce 500mA.

500mA, it's what a standard USB 1.0 plug provides. A battery of 500 parallel piles of 5 lemon cells would be required to produce the same energy (or one pile of five one-meter-gutter cells). Fortunately, your smartphone should be able to recharge even with a smaller current; it will just take longer. Some are able to take advantage of first 1mA, but wont actually recharge themselves if provided with less than their own idle power consumption (~20mA, screen off, airplane mode). Others will require at least the standard power to consider that they are plugged. Also, most smartphones will accept bigger charging power (a common value is 1A) which leads again to bigger cells. But the quicker you recharge your battery, the more the battery will be damaged by the process. You can't prevent the battery from getting worn by the recharge process, but slower recharge will wear it down less.

Using a magnesium electrode instead of the zinc one makes a cell of a larger voltage (1.6V), and could allow you to build piles of only 3 cells. Magnesium is the soft-metal component of tempest friction-based sparking lighters that are usually embeded in survival kits. Other metals can be used also such as: lead, iron, aluminium..

As stated in [Wikipedia](https://en.wikipedia.org/wiki/Lemon_battery), the energy comes from the chemical change in the zinc when it dissolves into the acid. The energy does not come from the lemon or potato. So the cells will run as long as you have zinc and lemon juice. One should pay attention to avoid evaporation of the lemon juice or salt water.

Volta himself would have interpolated a paper-towel soaked in salt water between metal discs (such as brown copper pennies, and silvery zinc ones) in order to have a pile of cells plugged in series, in a compact shape.

Lemon powered clocks have been reported to run for several months with uncut lemons. Penny piles tend to dry quicker, reportedly 6h. As long as you have metal in your electrodes, refilling electrolyte will reactivate the cells.

**Aluminium-air cells**

Industrially produced "pure" aluminium (such as soda cans or aluminium foils) allows the making of more powerful cells, especially when paired with activated charcoal. A 10cm by 10cm cell made of these materials, with salt water electrolyte, produce 1V and 100mA.

Activated charcoal is usually used to filter aquarium water. Well burnt regular charcoal will be efficient too, once reduced to large grain powder, in order to contain the maximum air in it.

To make an aluminium-air cell, cut a 10cm by 10cm aluminium foil piece. Lay salt water soaked paper on it. Set a 1cm thick charcoal layer on the soaked paper. Put a bare copper wire across the carbon. Fold the edges of the aluminium foil, like you would close a burrito. The copper wire must not touch the aluminium. Press on the aluminium burrito to activate it. The volts appear between the copper wire (being the positive pole) and the aluminium foil (negative one).

The drawback here is that aluminium gets oxidized within tens of minutes. Aluminium oxidation sticks on the aluminium part, preventing the reaction from continuing. The aluminium anode can be mechanically brushed to remove oxidation or bleach can be added to the salt water to reactivate the cell.
