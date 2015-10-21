/// <reference path='typings/node/node.d.ts' />

import fs = require('fs');

const allPhrases = fs.readFileSync('web2.txt', 'utf8').split('\n')
const blank = "_";

var phrases = {};

allPhrases.forEach((phrase) => {
    var n = '' + phrase.length;
    if (phrases.hasOwnProperty(n)) {
        phrases[n].push(phrase);
    } else {
        phrases[n] = [phrase];
    }
})


function phrasesMatching(pattern: string) {
    var n = pattern.length;
    var matches = [];
    phrases[n].forEach( (phrase) => {
        var matching = true;
        for (var i = 0; i < n; i++) {
            if (pattern[i] !== blank && pattern[i] !== phrase[i]) {
                matching = false;
                break;
            }
        }
        if (matching) {
            matches.push(phrase);
        }
    });
    return matches;
}

function isFull(puzzle: string[]) {
    const n = puzzle.length;
    var isFull = true;
    puzzle.forEach( (row) => {
        if (row.indexOf(blank) !== -1) {
            isFull = false;
        }
    })
    return isFull;
}

class move {
    x: number;
    y: number;
    letter: string;
}

class delta {
    x: number;
    y: number;
    moves: move[];
}

function isUnfullRow(puzzle, x, y) {
    const n = puzzle.length;
    for (var ix = x; ix < n; ix++) {
        if (puzzle[y][ix] == blank) {
            return true;
        }
        ix++;
    }
    return false;
}

function fillRow(puzzle, x: number, y: number) {
    var pattern = "";
    const n = puzzle.length;
    for (var ix = x; ix < n; ix++) {
        pattern += puzzle[y][ix];
    }

    const phrases = phrasesMatching(pattern);

    const phrase = phrases[0];

    var delta: delta = {
        x: x,
        y: y,
        moves: []
    }

    for (var ix = x; ix < n; ix++) {
        if (puzzle[y][ix] == blank) {
            delta.moves.push({
                x: ix,
                y: y,
                letter: phrase[ix]
            })
        }
    }

    return delta;
}

function applyDelta(delta: delta, puzzle: string[]) {
    delta.moves.forEach((move) => {
        console.log(move);
        console.log(puzzle[move.y][move.x]);
        puzzle[move.y][move.x] = move.letter[0];
    })
    console.log(puzzle);
}

function undoDelta(delta: delta, puzzle: string[]) {
    delta.moves.forEach((move) => {
        puzzle[move.y][move.x] = blank;
    })
}

function fillPuzzle(puzzle: string[]) {
    const n = puzzle.length;

    var x = 0;
    var y = 0;

    var deltas = [];

    console.log("Initial puzzle:");
    console.log(puzzle);
    console.log("Filling puzzle...");

    if (isUnfullRow(puzzle, x, y)) {
        var delta = fillRow(puzzle, x, y);
        console.log(delta);
        applyDelta(delta, puzzle);
        deltas.push(delta);
    }
    console.log(puzzle);
    // choose a row / column
    // fill row / column
    // unfill row / column

    console.log("Done filling puzzle!");
}

const unfullPuzzle = [
    "ab_", "def", "gh "
];

const fullPuzzle = [
    "ab", "cd"
];

fillPuzzle(unfullPuzzle);
