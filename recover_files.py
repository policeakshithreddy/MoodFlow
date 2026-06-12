import json
import os

ids = [
    "2dca91c5-bd3b-4ed3-9ea8-f3d342ddc572",
    "6adae820-c167-462a-bd7f-d6693d568c2f",
    "dfdd97e3-9e29-43b4-b9ef-17a069a8f496",
    "5ff82a65-a376-4b00-b747-f92b42374191",
    "137d1711-a730-4af7-805b-3797345a8739",
    "6ce4ad0f-a3f2-42db-87f6-90587af300cc",
    "6263f4bf-0e70-4ab5-93cc-e2817e710d40"
]

base_dir = "/Users/akku/.gemini/antigravity/brain"
for cid in ids:
    transcript_path = os.path.join(base_dir, cid, ".system_generated", "logs", "transcript.jsonl")
    if not os.path.exists(transcript_path): continue
    with open(transcript_path, "r") as f:
        for line in f:
            try:
                data = json.loads(line)
                if "tool_calls" in data:
                    for tc in data["tool_calls"]:
                        if tc["name"] == "write_to_file":
                            args = tc["args"]
                            if "TargetFile" in args and "CodeContent" in args:
                                target = args["TargetFile"]
                                if target.startswith('"') and target.endswith('"'):
                                    target = json.loads(target)
                                content = args["CodeContent"]
                                if content.startswith('"') and content.endswith('"'):
                                    content = json.loads(content)
                                os.makedirs(os.path.dirname(target), exist_ok=True)
                                with open(target, "w") as out:
                                    out.write(content)
                                print(f"Restored {target}")
            except Exception as e:
                pass
