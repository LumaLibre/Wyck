/**
 * GitHub source-link helpers for BiomesAPI docs.
 *
 * Every source link in the docs points at the same repo and, almost always,
 * the same Java source root. These helpers collapse that boilerplate so a
 * link is just the part that actually varies — the file path under the
 * package root, and optionally a line number.
 *
 *   import { repoFile } from "@/lib/links";
 *
 *   repoFile("renderer/setter/BiomeSetter.java");
 *   // -> https://github.com/.../blob/main/api/src/main/java/me/outspending/biomesapi/renderer/setter/BiomeSetter.java
 *
 *   repoFile("renderer/packet/data/PhonyCustomBiome.java", { line: 90 });
 *   // -> ...PhonyCustomBiome.java#L90
 *
 * For paths that live outside the Java source root, pass `root: false` and
 * give the full repo-relative path instead.
 */
const REPO_BLOB = "https://github.com/LumaLibre/BiomesAPI/blob";
const DEFAULT_REF = "main";
const SRC_ROOT = "api/src/main/java/me/outspending/biomesapi";

export interface RepoFileOptions {
    ref?: string;
    line?: number;
    root?: boolean;
}

export function repoFile(path: string, options: RepoFileOptions = {}): string {
    const { ref = DEFAULT_REF, line, root = true } = options;

    const cleaned = path.replace(/^\/+/, "");
    const relative = root ? `${SRC_ROOT}/${cleaned}` : cleaned;
    const url = `${REPO_BLOB}/${ref}/${relative}`;

    return typeof line === "number" ? `${url}#L${line}` : url;
}