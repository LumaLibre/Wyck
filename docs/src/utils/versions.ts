const REPO_BASE = "https://repo.wyck.dev";
const DEV_REPO = "snapshots"; // commit-tagged dev builds live here
const GAV_PATH = "dev/wyck/Wyck";
const GITHUB_REPO = "LumaLibre/Wyck";
const FALLBACK = "unknown";

/**
 * Stable release: latest GitHub release tag.
 * @param prefix only consider tags starting with this prefix, ex: "3 "
 */
export async function getLatestRelease(prefix?: string): Promise<string> {
    try {
        const endpoint = prefix ? "releases?per_page=100" : "releases/latest";
        const res = await fetch(
            `https://api.github.com/repos/${GITHUB_REPO}/${endpoint}`,
            { headers: { Accept: "application/vnd.github+json" } }
        );
        if (!res.ok) {
            console.error(`[versions] github ${res.status}`);
            return FALLBACK;
        }
        const data = await res.json();
        const release = prefix
            ? data.find((r: { tag_name: string; draft: boolean; prerelease: boolean }) =>
                !r.draft && !r.prerelease && r.tag_name.replace(/^v/, "").startsWith(prefix))
            : data;
        return (release?.tag_name ?? FALLBACK).replace(/^v/, ""); // drop leading v if present
    } catch (err) {
        console.error("[versions] github threw:", err);
        return FALLBACK;
    }
}

/**
 * Latest dev build: newest version deployed to the Reposilite repo.
 * @param prefix only consider versions starting with this prefix. ex: "3 "
 */
export async function getLatestSnapshot(prefix?: string): Promise<string> {
    const url = `${REPO_BASE}/${DEV_REPO}/${GAV_PATH}/maven-metadata.xml`;
    try {
        const res = await fetch(url, { headers: { "Cache-Control": "no-cache" } });
        if (!res.ok) {
            console.error(`[versions] metadata ${res.status} for ${url}`);
            return FALLBACK;
        }
        const xml = await res.text();
        // <versions> lists entries in deploy order, so the last is the newest.
        const versions = [...xml.matchAll(/<version>(.*?)<\/version>/g)]
            .map((m) => m[1])
            .filter((v) => !prefix || v.startsWith(prefix));
        if (versions.length) return versions[versions.length - 1];
        if (prefix) return FALLBACK;
        return xml.match(/<latest>(.*?)<\/latest>/)?.[1] ?? FALLBACK;
    } catch (err) {
        console.error(`[versions] metadata threw for ${url}:`, err);
        return FALLBACK;
    }
}