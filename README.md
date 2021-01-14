# Welcome to aTFC

## Why?

Because we didn't like how [TFC-TNG][2] dropped the food mechanics from the original Mod, and were not happy with the current performance of [TFC+][1] which is still being based on Minecraft 1.7.10. We also wanted to dabble in writing Minecraft mods, after playing the game for a decade.

## What does aTFC stand for?

The term *aTFC* stands for *another* TFC, because this is yet another rewrite of the most amazing Minecraft mod that was ever made. Our goal is to recreate the original TFC mod, running on the newest Minecraft version (1.16.4), as well as introduce changes we enjoyed by both [TFC+][1] and [TFC-TNG][2].

This mod is different to...
* [TFC+][1]: as it is not based on the original code for Minecraft 1.7.10
* [TFC-TNG][2] as it is not dropping the food mechanics

## Contributors

* [Michael "dot_Sp0T" Stocker](https://github.com/dotSp0T)
* [Patrick "McDonnough" Bächli](https://github.com/McDonnough)

# Development

## How to build & run

1. run `gradlew build`
2. run `gradlew runData` to generate json files (models, blockstates, ...)
3. run `gradlew build`
4. run `gradlew runClient` or use the `runClient.launch` config in eclipse to start the game

After the first build you only have to run steps 2, 3 and 4. If no generators were touched, step 4. is enough.

## Versioning

* 0.0.x Enyo

## Coordinates

`x` increments towards `east` and decrements towards `west`  
`y` increments towards `up` and decrements towards `down`  
`z` increments towards `south` and decrements towards `north`  

## Tools

* **Loot-Table Generator**: https://misode.github.io/loot-table/
* **SRG-Names Lookup**: https://mcp.thiakil.com/

## Formats

* **blockstates**: https://minecraft.gamepedia.com/Model
* **loot-tables**: https://minecraft.gamepedia.com/Loot_table
* **resource-packs**: https://minecraft.gamepedia.com/Resource_Pack

* **RenderTypes**: https://greyminecraftcoder.blogspot.com/2020/04/block-rendering-1144.html

## Links

* **Forge Docu**: https://mcforge.readthedocs.io/en/1.16.x/
* **Cadiboo Forge 1.15.1 Tutorials**: https://cadiboo.github.io/tutorials/1.15.1/forge/
* **Modding with Forge 1.16.4**: https://youtube.com/playlist?list=PLaevjqy3Xufavi5mWXvWnGmwRylL-QZy7
* **Modding with Forge 1.16.x**: https://youtube.com/playlist?list=PLGProEDTdBjghW5IZsiWno6e9QAkHChyQ
* **Modding with Forge 1.11**: https://www.youtube.com/playlist?list=PLGProEDTdBjjNQrr1G4Nv3oqqQ14hZtYV
* **Modding with Forge 1.8**: https://www.youtube.com/playlist?list=PLiFAb_ju1TahVZ1uOer7T3m_17crt15kp

* **Modding for 1.14.4+**: https://greyminecraftcoder.blogspot.com/p/list-of-topics-1144.html

**Texturing**:
- Multi-Texturing: https://mcmodhelp.fandom.com/wiki/Multi-Texturing_Blocks

**Modelling**:
- Blockmodels: https://mcmodhelp.fandom.com/wiki/Multi-Texturing_Blocks
- MrCrayfish Model Creator: https://mrcrayfish.com/tools.php?id=mc



[1]: https://plus.terrafirmacraft.com/
[2]: https://tng.terrafirmacraft.com/Main_Page