# Fallout Curve Table
Given a (set of) curve table files from Fallout 76, generates a format usable by the 
[CurveTable template](https://fallout.fandom.com/wiki/Template:CurveTable) on 
[Nukapedia](https://fallout.fandom.com/).
Prevents typos and makes the whole process a lot easier.

Curve table files are JSON files and can be extracted from the `SeventySix - Startup.ba2` file using a tool such as
[Bethesda Archive Extractor](https://www.nexusmods.com/skyrimspecialedition/mods/974).

## Usage
1. Download the archive for your system on
   [the releases page](https://github.com/FWDekker/fallout-curve-tables/releases).
2. Unpack the archive in any folder.
3. Open a terminal and navigate to the `bin/` folder in the extracted archive.
4. Run the program with `falloutcurvetables [options]` (Windows) or `./falloutcurvetables [options]` (Linux/macOS).
   See below for a list of options.

### Options
```
Usage: <main class> [options]
  Options:
  * -d, --destination
      The file to write output to.
    --format
      The format to print each file as. `%n` for file name excluding prefix 
      and suffix, `%m` for `%n` but right-padded with whitespaces so that all 
      names are of equal length, and `%v` for curve values.
      Default:     -->|%m =%v<!--
    --help
      Shows this information.
    --prefix
      The string that all source file names should begin with.
      Default: <empty string>
  * -s, --source
      The file or directory to read.
    --suffix
      The string that all source file names should end with, including the 
      file extension.
      Default: .json
```

### Examples
#### HP
Let's say you have a directory `misc/curvetables/json/creatures/health/` containing the health curve tables for
Fallout 76's creatures.
Each filename has the format `health_<creature>.json`.
Then the following command generates the appropriate output for the 
[HP template](https://fallout.fandom.com/wiki/Template:Stats_creature_FO76/HP):
```bash
java -jar fallout-curve-table.jar -s misc/curvetables/json/creatures/health/ -d my_output.txt --prefix "health_"
```

#### DR
Let's say you have a directory `misc/curvetables/json/creatures/armor/` containing the damage and energy resistance 
curve tables for Fallout 76's creatures.
Each filename has the format `armor_<creature>_dr.json` or `armor_<creature>_er.json`, but you're only interested in 
those with the former.
Then the following command generates the appropriate output for the 
[DR template](https://fallout.fandom.com/wiki/Template:Stats_creature_FO76/DR):
```bash
java -jar fallout-curve-table.jar -s misc/curvetables/json/creatures/armor/ -d my_output.txt --prefix "armor_" --suffix "_dr.json"
```

## Development
### Requirements
* JDK 11 (not newer)

### Releasing
```bash
gradlew runtimeZip  # Create distribution ZIPs in the `build/image/` directory
```

#### JDK downloads
Compiling distributions requires access to JDKs for the target platforms.
These JDKs are automatically downloaded to `build/jdks/`.
This directory can be overridden with environment variable `JLINK_JDK_DIR`.
