const moods = [
  {
    label: "Reflective",
    description: "Quiet · Aware · Open",
    a: "#6fb1c4",
    b: "#d7d3bf",
    c: "#a9c8bc"
  },
  {
    label: "Calm",
    description: "Balanced · Soft · At ease",
    a: "#50c6c4",
    b: "#ccebdc",
    c: "#f3c779"
  },
  {
    label: "Focused",
    description: "Clear · Grounded · Present",
    a: "#84c9a4",
    b: "#f3c779",
    c: "#f0ad75"
  },
  {
    label: "Inspired",
    description: "Creative · Energized · Optimistic",
    a: "#50c6c4",
    b: "#f3c779",
    c: "#f38b78"
  },
  {
    label: "Joyful",
    description: "Bright · Connected · Light",
    a: "#f6c06a",
    b: "#f38b78",
    c: "#f6a7b9"
  }
];

const moodRange = document.querySelector("#moodRange");
const moodLogging = document.querySelector("#moodLogging");
const liveOrb = document.querySelector("#liveOrb");
const moodLabel = document.querySelector("#moodLabel");
const moodDescription = document.querySelector("#moodDescription");

function setMood(index) {
  const mood = moods[index];
  moodLogging.style.setProperty("--log-a", mood.a);
  moodLogging.style.setProperty("--log-b", mood.b);
  moodLogging.style.setProperty("--log-c", mood.c);
  liveOrb.style.setProperty("--orb-a", mood.a);
  liveOrb.style.setProperty("--orb-b", mood.b);
  liveOrb.style.setProperty("--orb-c", mood.c);
  moodLabel.textContent = mood.label;
  moodDescription.textContent = mood.description;
}

if (moodRange) {
  moodRange.addEventListener("input", (event) => {
    setMood(Number(event.target.value));
  });
  setMood(Number(moodRange.value));
}

document.querySelectorAll(".chip").forEach((chip) => {
  chip.addEventListener("click", () => {
    const isSelected = chip.classList.toggle("selected");
    chip.setAttribute("aria-pressed", String(isSelected));
  });
});

document.querySelectorAll(".energy-options button").forEach((button) => {
  button.addEventListener("click", () => {
    button.parentElement.querySelectorAll("button").forEach((item) => {
      item.classList.remove("selected");
    });
    button.classList.add("selected");
  });
});

document.querySelectorAll(".filter-tabs button").forEach((button) => {
  button.addEventListener("click", () => {
    button.parentElement.querySelectorAll("button").forEach((item) => {
      item.classList.remove("active");
    });
    button.classList.add("active");
  });
});
