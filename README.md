# Your Options Shall Be Saved (YOSBS)

Your Options Shall Be Saved (YOSBS) is a lightweight utility mod that lets modpack creators define default configuration files without overwriting player preferences.

Updating a modpack often resets custom settings like keybinds or mod configs, because most launchers replace the entire `config/` folder.

YOSBS solves this by copying any file placed in `config/yosbs/` to its proper location only once on first launch — and never again.

This gives full control to the pack author:

- Files inside `config/yosbs/` are treated as user-owned: copied once, then left untouched
- Files inside `config/` behave as usual: they will be replaced on each update

That way, you decide which configs should persist, and which should reset."

## Links
- **Modrinth**: https://modrinth.com/project/1Gw7bnTq
- Curseforge: https://www.curseforge.com/minecraft/mc-mods/yosbs

## Features
- **Zero-config**: just drop the defaults under `/config/yosbs/…` (or legacy `/config/yosbr`)
- Works for **any** file – `options.txt`, mod configs, `optionsshaders.txt`, etc
- Runs in the **pre-launch phase**, before Fabric/Forge starts loading the rest of the mods


## Compatibility
|                    | Version(s) |
|--------------------|------------|
| **Minecraft**      | 1.14 → 1.21.x |
| **Loader**         | Fabric 0.4+  |
| **Java**           | 8 or newer |
| **Arch/** 🔒 | Client & Server safe |


## Usage examples

| Want to ship… | Do this before first launch |
|---------------|----------------------------|
| A default `options.txt` | Place your file at `/config/yosbs/options.txt` |
| A REI config | `/config/yosbs/config/roughlyenoughitems/config.json5` |

## Credits
Inspired by the original [Your Options Shall Be Respected – YOSBR](https://modrinth.com/mod/yosbr) by *shedaniel*
