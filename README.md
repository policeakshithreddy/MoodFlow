# MoodFlow UI Prototype

High-fidelity mobile UI concept and code-native prototype for MoodFlow.

## Files

- `index.html` - six mobile screens: Onboarding, Home, Mood Logging, Mood Details, Journey, Insights.
- `styles.css` - logo-derived color tokens, Manrope typography, organic orb system, native-feasible motion.
- `script.js` - small interactions for mood spectrum, influence chips, energy selection, and journey filters.
- `assets/moodflow-logo.png` - supplied logo copied into the prototype assets.
- `assets/moodflow-concept-board.png` - generated visual concept reference used for implementation.

## Run

Open `index.html` directly, or serve this folder:

```bash
python3 -m http.server 4173
```

Then visit `http://127.0.0.1:4173`.

## Implementation Notes

The visual system is built from the logo palette:

- Teal for calm and reflective states.
- Warm sand for balance and focus.
- Soft coral for inspired and joyful states.

The orb, mood canvas, curved insight chart, chips, and soft memory surfaces are all code-native and realistic to recreate in SwiftUI or Jetpack Compose with gradients, blur, canvas shapes, and shape morphing.
