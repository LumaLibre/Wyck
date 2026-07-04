const REPO_BLOB = "https://github.com/LumaLibre/Wyck/blob";
const DEFAULT_REF = "main";
const SRC_ROOT = "api/src/main/java/dev/wyck";
const JAVADOC_BASE = "https://wyck.dev/javadoc";
const PACKAGE_ROOT = "dev/wyck";

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

export interface JavadocLinkOptions {
    member?: string;
    root?: boolean;
}

export function javadocLink(path: string, options: JavadocLinkOptions = {}): string {
    const { member, root = true } = options;

    const cleaned = path.replace(/^\/+/, "").replace(/\.java$/, "");
    const classPath = root ? `${PACKAGE_ROOT}/${cleaned}` : cleaned;
    const url = `${JAVADOC_BASE}/${classPath}.html`;

    if (!member) return url;
    const anchor = member.startsWith("#") ? member.slice(1) : member;
    return `${url}#${anchor}`;
}