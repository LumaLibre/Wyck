const REPO_BASE = "https://repo.jsinco.dev";
const DEV_REPO = "snapshots"; // commit-tagged dev builds live here
const GAV_PATH = "me/outspending/biomesapi/BiomesAPI";
const GITHUB_REPO = "LumaLibre/BiomesAPI";
const FALLBACK = "unknown";

/** Stable release: latest GitHub release tag. */
export async function getLatestRelease(): Promise<string> {
    try {
        const res = await fetch(
            `https://api.github.com/repos/${GITHUB_REPO}/releases/latest`,
            { headers: { Accept: "application/vnd.github+json" } }
        );
        if (!res.ok) {
            console.error(`[versions] github ${res.status}`);
            return FALLBACK;
        }
        const data = await res.json();
        return (data.tag_name ?? FALLBACK).replace(/^v/, ""); // drop leading v if present
    } catch (err) {
        console.error("[versions] github threw:", err);
        return FALLBACK;
    }
}

/** Latest dev build: newest version deployed to the Reposilite repo. */
export async function getLatestSnapshot(): Promise<string> {
    const url = `${REPO_BASE}/${DEV_REPO}/${GAV_PATH}/maven-metadata.xml`;
    try {
        const res = await fetch(url, { headers: { "Cache-Control": "no-cache" } });
        if (!res.ok) {
            console.error(`[versions] metadata ${res.status} for ${url}`);
            return FALLBACK;
        }
        const xml = await res.text();
        // <versions> lists entries in deploy order, so the last is the newest.
        const versions = [...xml.matchAll(/<version>(.*?)<\/version>/g)].map((m) => m[1]);
        if (versions.length) return versions[versions.length - 1];
        return xml.match(/<latest>(.*?)<\/latest>/)?.[1] ?? FALLBACK;
    } catch (err) {
        console.error(`[versions] metadata threw for ${url}:`, err);
        return FALLBACK;
    }
}