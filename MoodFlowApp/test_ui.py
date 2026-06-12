import subprocess
import time
import re

def tap(x, y):
    subprocess.run(["adb", "shell", "input", "tap", str(x), str(y)])

def get_dump():
    subprocess.run(["adb", "shell", "uiautomator", "dump"])
    subprocess.run(["adb", "pull", "/sdcard/window_dump.xml", "window_dump.xml"])
    with open("window_dump.xml", "r") as f:
        return f.read()

def tap_text(text, dump):
    pattern = r'bounds="\[(\d+),(\d+)\]\[(\d+),(\d+)\]"[^>]*text="' + text + r'"'
    match = re.search(pattern, dump)
    if not match:
        # maybe it's in content-desc or it's a clickable container containing text
        pattern2 = r'bounds="\[(\d+),(\d+)\]\[(\d+),(\d+)\]"[^>]*clickable="true".*?text="' + text + r'"'
        match = re.search(pattern2, dump)
    if match:
        x1, y1, x2, y2 = map(int, match.groups())
        x = (x1 + x2) // 2
        y = (y1 + y2) // 2
        tap(x, y)
        print(f"Tapped {text} at {x}, {y}")
        return True
    return False

# Try to click Log Mood
dump = get_dump()
if not tap_text("Log Mood", dump):
    tap(500, 1600) # Fallback

time.sleep(1)

# Now we are on Step 1 (MoodSelectionStep).
# The continue button is an IconButton at the bottom. It doesn't have text, it has an icon!
# Wait, the Next step is an IconButton with imageVector = Icons.Rounded.ArrowForward. Content description is "Continue".
dump = get_dump()
pattern = r'bounds="\[(\d+),(\d+)\]\[(\d+),(\d+)\]"[^>]*content-desc="Continue"'
match = re.search(pattern, dump)
if match:
    x1, y1, x2, y2 = map(int, match.groups())
    tap((x1 + x2) // 2, (y1 + y2) // 2)
    print("Tapped Continue")
else:
    # It might be in the bottom right corner
    tap(1150, 2750)
    print("Tapped fallback for Continue")

