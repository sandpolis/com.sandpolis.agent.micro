//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//

plugins {
	id("sandpolis-module")
	id("sandpolis-soi")
}

val protoDependencies = listOf(
	":module:com.sandpolis.core.foundation",
	":module:com.sandpolis.core.instance",
	":module:com.sandpolis.core.net",
	":plugin:com.sandpolis.plugin.snapshot"
)

if (project.getParent() == null) {
	repositories {
		protoDependencies.map { it.split(".").takeLast(2) }.forEach {
			maven("https://maven.pkg.github.com/sandpolis/com.sandpolis.${it.first()}.${it.last()}") {
				credentials {
					username = System.getenv("GITHUB_ACTOR")
					password = System.getenv("GITHUB_TOKEN")
				}
			}
		}
	}

	val proto by configurations.creating

	dependencies {
		protoDependencies.map { it.split(".").takeLast(2) }.forEach {
			proto("com.sandpolis:${it.first()}.${it.last()}-rust:+@zip")
		}
	}

	val assembleProto by tasks.creating(Copy::class) {

		into("src/main/rust/gen")

		proto.files.forEach { dep ->
			var path = dep.absolutePath.split("-").takeLast(3)
			path = path.first().split("\\.|/".toRegex()).takeLast(2)

			into("com.sandpolis.${path[0]}.${path[1]}") {
				with(copySpec {
					from(zipTree(dep))
				})
			}
		}
	}

} else {

	val assembleProto by tasks.creating(Copy::class) {

		into("src/main/rust/gen")

		for (dep in protoDependencies) {
			dependsOn("${dep}:fixRustImports")

			into(dep.substring(dep.lastIndexOf(":") + 1)) {
				with (copySpec {
					from(project(dep).file("gen/main/rust"))
				})
			}
		}
	}
}

val assemble by tasks.creating(Exec::class) {
	dependsOn(tasks.findByName("assembleProto"))
	workingDir(project.getProjectDir())
	commandLine(listOf("cargo", "build"))
}
