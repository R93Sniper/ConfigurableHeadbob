# Configurable Headbob
Configurable Headbob is a small fabric mod designed to allow players to configure the headbob functionality of Minecraft!


## How it works
In a nutshell, we just intercept the menu call in accessibility options and break up vanilla's components. From there, we just let you configure those as you want!</br>
By default, it just splits up the viewmodel and camera's intensity and speed scales, but you can enable advanced mode to individually configure their components as well.</br>
While not explicitly necessary, it lets you really tune what you do and don't like about Minecraft's headbobbing.</br>
Still don't want to deal with headbobbing? You can still turn everything off just like in Vanilla! (Seriously, its the same button and code!)

## License

Configurable Headbob is licensed under the **GNU General Public License,
version 3 only**, with the **Minecraft linking exception** appended to
[LICENSE](LICENSE). The complete official GPLv3 text is reproduced unchanged
first, followed by the separate exception.

The exception uses the existing [Minecraft linking exception published by
triphora](https://gist.github.com/triphora/588f353802a3b0ea649e4fc85f75e583),
linked from [Modrinth's licensing guide](https://modrinth.com/news/article/licensing-guide/).
It permits combining the mod with Minecraft under the exception's terms;
the mod itself remains GPL-covered.

License identifier: `LicenseRef-GPL-3.0-only-with-Minecraft-linking-exception`.
This is a project license reference, not an official SPDX identifier for the
Minecraft exception. See LICENSE for the full terms.

## Disclosure Notice
Per Modrinth's rules, here's a small disclosure about this project.</br>
AI was used in a *purely* assistive capacity for this project. I asked it questions and had it explain how some code needs to be structured. **I did not use any AI code output directly**.</br>
The only bit I copied from AI output were the mixin target paths, since they're long and obnoxious to deal with. I also had it deal with the mixed license in the section above this.</br>
Past that, every character written in this project is either written by me or is part of the fabric template.