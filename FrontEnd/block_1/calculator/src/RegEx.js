// '2 (+-*/) 2 = ' or '2.5 (+-*/) 2 = '
export const STATEMENT_IS_CORRECT = /^-*(\d+\.*\d*)\s+[/*+-]\s+-*\d+\s+[/*+\-=]\s+$/
// '2 = '
export const STATEMENT_IS_BAD = /^-*\d+\s*=\s*$/
// '2 + 2 (+-*/) '
export const END_OF_STATEMENT_IS_SIMBL = /\s*[-+/*]\s*$/
// '2 + 2 = '
export const END_OF_STATEMENT_IS_EQUALS = /\s*=\s*$/
// '2'
export const IS_NUMBER = /\d/
// '+-/*='
export const IS_MINUS = /-/
export const IS_SIMBL = /[+\-=/*]/
export const IS_EQUALS = /=/