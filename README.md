# Fallout Curve Table
Given a (set of) curve table files from Fallout 76, turns them into part of a MediaWiki template used on Nukapedia. Prevents typos and makes the whole process easier.

Curve table files are JSON files and can be extracted from the BSAs.

## Usage
1. Download the jar on [the releases page](https://github.com/FWDekker/fallout-curve-tables/releases).
2. Open a terminal and navigate to where you downloaded the jar.
3. Run the jar with `java -jar fallout-curve-table.jar [options]`.

### Options
```
Usage: <main class> [options]
  Options:
  * -d, --description
      The file to write output to.
      Default: <empty string>
    --format
      The format to print each file as. `%n` for file name excluding prefix 
      and extension, `%m` for `%n` but right-padded with whitespaces so that 
      all names are of equal length, and `%v` for curve values.
      Default:       -->|%m =%v<!--
    --help
      Shows this information.
    --prefix
      The string that all source files should begin with.
      Default: <empty string>
  * -s, --source
      The file or directory to read.
      Default: <empty string>
```

## Development
```bash
gradlew assemble  # Create the jar in the `build/libs` directory
```
