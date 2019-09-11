package com.fwdekker.falloutcurvetable

import com.beust.jcommander.JCommander
import com.beust.jcommander.Parameter
import com.beust.jcommander.ParameterException
import java.io.File


class Args {
    @Parameter(
        names = ["-s", "--source"],
        description = "The file or directory to read.",
        required = true
    )
    lateinit var source: String

    @Parameter(
        names = ["-d", "--description"],
        description = "The file to write output to.",
        required = true
    )
    lateinit var destination: String

    @Parameter(
        names = ["--prefix"],
        description = "The string that all source file names should begin with.",
        required = false
    )
    var prefix: String = ""

    @Parameter(
        names = ["--suffix"],
        description = "The string that all source file names should end with, including the file extension.",
        required = false
    )
    var suffix: String = ".json"

    @Parameter(
        names = ["--format"],
        description = "The format to print each file as. `%n` for file name excluding prefix and extension, " +
            "`%m` for `%n` but right-padded with whitespaces so that all names are of equal length, " +
            "and `%v` for curve values.",
        required = false
    )
    var format: String = "    -->|%m =%v<!--"

    @Parameter(names = ["--help"], help = true, description = "Shows this information.")
    var help: Boolean = false
}


fun format(format: String, nameExtractor: (String) -> String, files: Array<File>): String {
    if (files.isEmpty())
        return ""

    val longestName = files.map { nameExtractor(it.name) }.maxBy { it.length }?.length
        ?: throw IllegalStateException("Unexpected empty list.")
    val nameFormatter: ((String) -> String) = { it.padEnd(longestName, ' ') }

    return files.joinToString("\n") {
        val name = nameExtractor(it.name)
        format.replace("%n", name).replace("%m", nameFormatter(name)).replace("%v", Curve.fromFile(it).toString())
    }
}


fun main(argv: Array<String>) {
    val args = Args()
    val commander = JCommander.newBuilder().addObject(args).build()

    try {
        commander.parse(*argv)
    } catch (e: ParameterException) {
        commander.usage()
        return
    }

    if (args.help) {
        commander.usage()
        return
    }

    val source = File(args.source)
    val destination = File(args.destination)
    val filter: ((File) -> Boolean) = { it.name.startsWith(args.prefix) && it.name.endsWith(args.suffix) }
    val nameExtractor: ((String) -> String) = { it.removePrefix(args.prefix).removeSuffix(args.suffix) }

    print("Processing files...")
    destination.writeText(source.let {
        format(
            args.format,
            nameExtractor,
            if (it.isDirectory)
                it.listFiles(filter) ?: throw IllegalArgumentException("Failed to read files from directory.")
            else
                arrayOf(it)
        )
    })
    println(" Done!")
}
