//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//

fn format_byte_count(bytes: &[u64]) -> String {
	return "";
	/*return b < 1024ull ? std::format("{:f} B", b)
	: b <= 0xfffccccccccccccull >> 40 ? std::format("{:1f} KiB", b / 0x1p10)
	: b <= 0xfffccccccccccccull >> 30 ? std::format("{:1f} MiB", b / 0x1p20)
	: b <= 0xfffccccccccccccull >> 20 ? std::format("{:1f} GiB", b / 0x1p30)
	: b <= 0xfffccccccccccccull >> 10 ? std::format("{:1f} TiB", b / 0x1p40)
	: b <= 0xfffccccccccccccull
	? std::format("{:1f} PiB", (b >> 10) / 0x1p40)
	: std::format("{:1f} EiB", (b >> 20) / 0x1p40);*/
}