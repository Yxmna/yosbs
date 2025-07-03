# Your Options Shall Be Saved (YOSBS)

Copies default option / config files from **`/config/yosbs`** to their
normal location at game launch – so your default settings are **always**
applied on fresh instances, new modpacks, etc

Modrinth: https://modrinth.com/project/1Gw7bnTq

---

## Features
- **Zero-config**: just drop the defaults under `/config/yosbs/…` (or legacy `/config/yosbr`)
- Works for **any** file – `options.txt`, mod configs, `optionsshaders.txt`, etc
- Runs in the **pre-launch phase**, before Fabric/Forge starts loading the rest of the mods

## Installation
1. Fabric Loader ≥ 0.4.0 *(No Fabric API required)*
2. Minecraft 1.14 – 1.21.x
3. Requires Java 8 or newer 
4. Put the YOSBS jar in `mods/` and launch once – the folder `/config/yosbs` is created automatically (or create it manually)


## Usage examples

| Want to ship… | Do this before first launch |
|---------------|----------------------------|
| A default `options.txt` | Place your file at `/config/yosbs/options.txt` |
| A REI config | `/config/yosbs/config/roughlyenoughitems/config.json5` |

At runtime, missing files are copied one-for-one, existing files are **never overwritten**

## Credits
Inspired by the original [Your Options Shall Be Respected – YOSBR](https://modrinth.com/mod/yosbr) by *shedaniel*
