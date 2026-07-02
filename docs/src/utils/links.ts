const REPO_BLOB = "https://github.com/LumaLibre/Wyck/blob";
const DEFAULT_REF = "main";
const SRC_ROOT = "api/src/main/java/dev/wyck";

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