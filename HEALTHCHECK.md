# IDLua's Healthcheck
## Description
In order to keep going on with this repository contributor change and to have a better understanding of the actual health of this plugin, we may refer to this file to know if anything is broken and organize a roadmap.

If you encounter any bug or would like to contribute by testing features, feel free to open issues on this repository, we'll update this file accordingly.

A board could be set up if needed to keep track of the work state of this plugin.

## States

At this time we'll consider 3 states to keep things simple:
 - <span style="color: #2ecc71">[OK]</span> - This feature is tested and fully functional.
 - <span style="color: #f39c12">[UN]</span> - This feature isn't tested yet. (Short for UNKNOWN).
 - <span style="color: #e74c3c">[KO]</span> - This feature has been tested and at least one thing is broken.

## Features
- <span style="color: #f39c12">[UN]</span> Semantic code highlighting of identifiers: global, local, parameter, upvalue
- <span style="color: #f39c12">[UN]</span> Highly configurable syntax highlighting
- <span style="color: #f39c12">[UN]</span> Syntax checking and error highlighting
- <span style="color: #e74c3c">[KO]</span> Code completion across all files including libraries and custom API's
- <span style="color: #e74c3c">[KO]</span> Code completions enhanced by type inference and flow analysis
- <span style="color: #f39c12">[UN]</span> Support for external API definitions to enhance completion including custom function signatures
- <span style="color: #f39c12">[UN]</span> LuaDoc auto-generation with highlighting and folding
- <span style="color: #f39c12">[UN]</span> Quick Documentation (ctrl-Q/cmd-f1) for Lua APIs, and custom API's
- <span style="color: #f39c12">[UN]</span> Code Formatter 
- <span style="color: #f39c12">[UN]</span> Go to definition (ctrl-click/cmd-click)
- <span style="color: #f39c12">[UN]</span> Find Usages, Goto Symbol
- <span style="color: #f39c12">[UN]</span> Lua SDK REPL Console
- <span style="color: #f39c12">[UN]</span> Modules support for completions (**Experimental**)
- <span style="color: #f39c12">[UN]</span> Structure view / Code Outline 
- <span style="color: #f39c12">[UN]</span> Refactorings
  - <span style="color: #f39c12">[UN]</span> Safe Delete
  - <span style="color: #f39c12">[UN]</span> Rename Identifier
  - <span style="color: #f39c12">[UN]</span> Introduce Variable (**Experimental**)
- <span style="color: #f39c12">[UN]</span> Debugger (**Experimental**)
- <span style="color: #f39c12">[UN]</span> Code Inspections
  - <span style="color: #f39c12">[UN]</span> Unused assignment
  - <span style="color: #f39c12">[UN]</span> Suspicious global creation, helps catch leaked globals
  - <span style="color: #f39c12">[UN]</span> Unbalanced assignment statements, helps catch bugs in multiple assignment statements
- <span style="color: #f39c12">[UN]</span> Code Intentions
  - <span style="color: #f39c12">[UN]</span> Replace explicit string library calls like `string.len("foo")` with `("foo"):len()`
- <span style="color: #f39c12">[UN]</span> Some Automatic FrameXML Injections (World of Warcraft)
- <span style="color: #f39c12">[UN]</span> Type Inference Based Completions (**Experimental**)