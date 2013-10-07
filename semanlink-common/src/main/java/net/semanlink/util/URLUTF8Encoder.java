package net.semanlink.util;
import java.io.UnsupportedEncodingException;
/*
URLUTF8	URLUTF8 fn	URLEncoder
0	 	%00	%00	%00
1		%01	%01	%01
2		%02	%02	%02
3		%03	%03	%03
4		%04	%04	%04
5		%05	%05	%05
6		%06	%06	%06
7		%07	%07	%07
8		%08	%08	%08
9			%09	%09	%09
10	
	%0A	%0A	%0A
11		%0B	%0B	%0B
12		%0C	%0C	%0C
13	
	%0D	%0D	%0D
14		%0E	%0E	%0E
15		%0F	%0F	%0F
16		%10	%10	%10
17		%11	%11	%11
18		%12	%12	%12
19		%13	%13	%13
20		%14	%14	%14
21		%15	%15	%15
22		%16	%16	%16
23		%17	%17	%17
24		%18	%18	%18
25		%19	%19	%19
26		%1A	%1A	%1A
27		%1B	%1B	%1B
28		%1C	%1C	%1C
29		%1D	%1D	%1D
30		%1E	%1E	%1E
31		%1F	%1F	%1F
32	 	+	%20	+
33	!	!	!	%21
34	"	%22	%22	%22
35	#	%23	%23	%23
36	$	%24	%24	%24
37	%	%25	%25	%25
38	&	%26	%26	%26
39	'	'	'	%27
40	(	(	(	%28
41	)	)	)	%29
42	*	*	*	*
43	+	%2B	%2B	%2B
44	,	%2C	%2C	%2C
45	-	-	-	-
46	.	.	.	.
47	/	%2F	/	%2F
48	0	0	0	0
49	1	1	1	1
50	2	2	2	2
51	3	3	3	3
52	4	4	4	4
53	5	5	5	5
54	6	6	6	6
55	7	7	7	7
56	8	8	8	8
57	9	9	9	9
58	:	%3A	%3A	%3A
59	;	%3B	%3B	%3B
60	<	%3C	%3C	%3C
61	=	%3D	%3D	%3D
62	>	%3E	%3E	%3E
63	?	%3F	%3F	%3F
64	@	%40	%40	%40
65	A	A	A	A
66	B	B	B	B
67	C	C	C	C
68	D	D	D	D
69	E	E	E	E
70	F	F	F	F
71	G	G	G	G
72	H	H	H	H
73	I	I	I	I
74	J	J	J	J
75	K	K	K	K
76	L	L	L	L
77	M	M	M	M
78	N	N	N	N
79	O	O	O	O
80	P	P	P	P
81	Q	Q	Q	Q
82	R	R	R	R
83	S	S	S	S
84	T	T	T	T
85	U	U	U	U
86	V	V	V	V
87	W	W	W	W
88	X	X	X	X
89	Y	Y	Y	Y
90	Z	Z	Z	Z
91	[	%5B	%5B	%5B
92	\	%5C	%5C	%5C
93	]	%5D	%5D	%5D
94	^	%5E	%5E	%5E
95	_	_	_	_
96	`	%60	%60	%60
97	a	a	a	a
98	b	b	b	b
99	c	c	c	c
100	d	d	d	d
101	e	e	e	e
102	f	f	f	f
103	g	g	g	g
104	h	h	h	h
105	i	i	i	i
106	j	j	j	j
107	k	k	k	k
108	l	l	l	l
109	m	m	m	m
110	n	n	n	n
111	o	o	o	o
112	p	p	p	p
113	q	q	q	q
114	r	r	r	r
115	s	s	s	s
116	t	t	t	t
117	u	u	u	u
118	v	v	v	v
119	w	w	w	w
120	x	x	x	x
121	y	y	y	y
122	z	z	z	z
123	{	%7B	%7B	%7B
124	|	%7C	%7C	%7C
125	}	%7D	%7D	%7D
126	~	~	~	%7E
127		%7F	%7F	%7F
128	?	%C2%80	%C2%80	%C2%80
129	?	%C2%81	%C2%81	%C2%81
130	?	%C2%82	%C2%82	%C2%82
131	?	%C2%83	%C2%83	%C2%83
132	?	%C2%84	%C2%84	%C2%84
133	?	%C2%85	%C2%85	%C2%85
134	?	%C2%86	%C2%86	%C2%86
135	?	%C2%87	%C2%87	%C2%87
136	?	%C2%88	%C2%88	%C2%88
137	?	%C2%89	%C2%89	%C2%89
138	?	%C2%8A	%C2%8A	%C2%8A
139	?	%C2%8B	%C2%8B	%C2%8B
140	?	%C2%8C	%C2%8C	%C2%8C
141	?	%C2%8D	%C2%8D	%C2%8D
142	?	%C2%8E	%C2%8E	%C2%8E
143	?	%C2%8F	%C2%8F	%C2%8F
144	?	%C2%90	%C2%90	%C2%90
145	?	%C2%91	%C2%91	%C2%91
146	?	%C2%92	%C2%92	%C2%92
147	?	%C2%93	%C2%93	%C2%93
148	?	%C2%94	%C2%94	%C2%94
149	?	%C2%95	%C2%95	%C2%95
150	?	%C2%96	%C2%96	%C2%96
151	?	%C2%97	%C2%97	%C2%97
152	?	%C2%98	%C2%98	%C2%98
153	?	%C2%99	%C2%99	%C2%99
154	?	%C2%9A	%C2%9A	%C2%9A
155	?	%C2%9B	%C2%9B	%C2%9B
156	?	%C2%9C	%C2%9C	%C2%9C
157	?	%C2%9D	%C2%9D	%C2%9D
158	?	%C2%9E	%C2%9E	%C2%9E
159	?	%C2%9F	%C2%9F	%C2%9F
160	�	%C2%A0	%C2%A0	%C2%A0
161	�	%C2%A1	%C2%A1	%C2%A1
162	�	%C2%A2	%C2%A2	%C2%A2
163	�	%C2%A3	%C2%A3	%C2%A3
164	?	%C2%A4	%C2%A4	%C2%A4
165	�	%C2%A5	%C2%A5	%C2%A5
166	?	%C2%A6	%C2%A6	%C2%A6
167	�	%C2%A7	%C2%A7	%C2%A7
168	�	%C2%A8	%C2%A8	%C2%A8
169	�	%C2%A9	%C2%A9	%C2%A9
170	�	%C2%AA	%C2%AA	%C2%AA
171	�	%C2%AB	%C2%AB	%C2%AB
172	�	%C2%AC	%C2%AC	%C2%AC
173	?	%C2%AD	%C2%AD	%C2%AD
174	�	%C2%AE	%C2%AE	%C2%AE
175	�	%C2%AF	%C2%AF	%C2%AF
176	�	%C2%B0	%C2%B0	%C2%B0
177	�	%C2%B1	%C2%B1	%C2%B1
178	?	%C2%B2	%C2%B2	%C2%B2
179	?	%C2%B3	%C2%B3	%C2%B3
180	�	%C2%B4	%C2%B4	%C2%B4
181	�	%C2%B5	%C2%B5	%C2%B5
182	�	%C2%B6	%C2%B6	%C2%B6
183	�	%C2%B7	%C2%B7	%C2%B7
184	�	%C2%B8	%C2%B8	%C2%B8
185	?	%C2%B9	%C2%B9	%C2%B9
186	�	%C2%BA	%C2%BA	%C2%BA
187	�	%C2%BB	%C2%BB	%C2%BB
188	?	%C2%BC	%C2%BC	%C2%BC
189	?	%C2%BD	%C2%BD	%C2%BD
190	?	%C2%BE	%C2%BE	%C2%BE
191	�	%C2%BF	%C2%BF	%C2%BF
192	�	%C3%80	%C3%80	%C3%80
193	�	%C3%81	%C3%81	%C3%81
194	�	%C3%82	%C3%82	%C3%82
195	�	%C3%83	%C3%83	%C3%83
196	�	%C3%84	%C3%84	%C3%84
197	�	%C3%85	%C3%85	%C3%85
198	�	%C3%86	%C3%86	%C3%86
199	�	%C3%87	%C3%87	%C3%87
200	�	%C3%88	%C3%88	%C3%88
201	�	%C3%89	%C3%89	%C3%89
202	�	%C3%8A	%C3%8A	%C3%8A
203	�	%C3%8B	%C3%8B	%C3%8B
204	�	%C3%8C	%C3%8C	%C3%8C
205	�	%C3%8D	%C3%8D	%C3%8D
206	�	%C3%8E	%C3%8E	%C3%8E
207	�	%C3%8F	%C3%8F	%C3%8F
208	?	%C3%90	%C3%90	%C3%90
209	�	%C3%91	%C3%91	%C3%91
210	�	%C3%92	%C3%92	%C3%92
211	�	%C3%93	%C3%93	%C3%93
212	�	%C3%94	%C3%94	%C3%94
213	�	%C3%95	%C3%95	%C3%95
214	�	%C3%96	%C3%96	%C3%96
215	?	%C3%97	%C3%97	%C3%97
216	�	%C3%98	%C3%98	%C3%98
217	�	%C3%99	%C3%99	%C3%99
218	�	%C3%9A	%C3%9A	%C3%9A
219	�	%C3%9B	%C3%9B	%C3%9B
220	�	%C3%9C	%C3%9C	%C3%9C
221	?	%C3%9D	%C3%9D	%C3%9D
222	?	%C3%9E	%C3%9E	%C3%9E
223	�	%C3%9F	%C3%9F	%C3%9F
224	�	%C3%A0	%C3%A0	%C3%A0
225	�	%C3%A1	%C3%A1	%C3%A1
226	�	%C3%A2	%C3%A2	%C3%A2
227	�	%C3%A3	%C3%A3	%C3%A3
228	�	%C3%A4	%C3%A4	%C3%A4
229	�	%C3%A5	%C3%A5	%C3%A5
230	�	%C3%A6	%C3%A6	%C3%A6
231	�	%C3%A7	%C3%A7	%C3%A7
232	�	%C3%A8	%C3%A8	%C3%A8
233	�	%C3%A9	%C3%A9	%C3%A9
234	�	%C3%AA	%C3%AA	%C3%AA
235	�	%C3%AB	%C3%AB	%C3%AB
236	�	%C3%AC	%C3%AC	%C3%AC
237	�	%C3%AD	%C3%AD	%C3%AD
238	�	%C3%AE	%C3%AE	%C3%AE
239	�	%C3%AF	%C3%AF	%C3%AF
240	?	%C3%B0	%C3%B0	%C3%B0
241	�	%C3%B1	%C3%B1	%C3%B1
242	�	%C3%B2	%C3%B2	%C3%B2
243	�	%C3%B3	%C3%B3	%C3%B3
244	�	%C3%B4	%C3%B4	%C3%B4
245	�	%C3%B5	%C3%B5	%C3%B5
246	�	%C3%B6	%C3%B6	%C3%B6
247	�	%C3%B7	%C3%B7	%C3%B7
248	�	%C3%B8	%C3%B8	%C3%B8
249	�	%C3%B9	%C3%B9	%C3%B9
250	�	%C3%BA	%C3%BA	%C3%BA
251	�	%C3%BB	%C3%BB	%C3%BB
252	�	%C3%BC	%C3%BC	%C3%BC
253	?	%C3%BD	%C3%BD	%C3%BD
254	?	%C3%BE	%C3%BE	%C3%BE
 */
/*
 * THIS CLASS IS DERIVED FROM A SOURCE CODE PUBLISHED ON THE W3C WEB SITE
 * AT FOLLOWING LOCATION :
 * http://www.w3.org/International/URLUTF8Encoder.java
 * THIS ORIGINAL WORK IS PUBLISHED WITH FOLLOWING LICENSE :
 * http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * Here is the text of license:
 * W3C� SOFTWARE NOTICE AND LICENSE

http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231

This work (and included software, documentation such as READMEs, or other related items) is being provided by the copyright holders under the following license. By obtaining, using and/or copying this work, you (the licensee) agree that you have read, understood, and will comply with the following terms and conditions.

Permission to copy, modify, and distribute this software and its documentation, with or without modification, for any purpose and without fee or royalty is hereby granted, provided that you include the following on ALL copies of the software and documentation or portions thereof, including modifications:

The full text of this NOTICE in a location viewable to users of the redistributed or derivative work.
Any pre-existing intellectual property disclaimers, notices, or terms and conditions. If none exist, the W3C Software Short Notice should be included (hypertext is preferred, text is permitted) within the body of any redistributed or derivative code.
Notice of any changes or modifications to the files, including the date changes were made. (We recommend you provide URIs to the location from which the code is derived.)
THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.

COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.

The name and trademarks of copyright holders may NOT be used in advertising or publicity pertaining to the software without specific, written prior permission. Title to copyright in this software and any associated documentation will at all times remain with copyright holders.

____________________________________

This formulation of W3C's notice and license became active on December 31 2002. This version removes the copyright ownership notice such that this license can be used with materials other than those owned by the W3C, reflects that ERCIM is now a host of the W3C, includes references to this specific dated version of the license, and removes the ambiguous grant of "use". Otherwise, this version is the same as the previous version and is written so as to preserve the Free Software Foundation's assessment of GPL compatibility and OSI's certification under the Open Source Definition. Please see our Copyright FAQ for common questions about using materials from our site, including specific terms and conditions for packages like libwww, Amaya, and Jigsaw. Other questions about this notice can be directed to site-policy@w3.org.
 

Joseph Reagle <site-policy@w3.org>
Last revised $Id: URLUTF8Encoder.java,v 1.1 2009/02/19 18:06:56 fps Exp $
*/
/**
 * Provides methods <UL>
 * <LI> to encode any string into a URL-safe form.
 * Non-ASCII characters are first encoded as sequences of
 * two or three bytes, using the UTF-8 algorithm, before being
 * encoded as %HH escapes. </LI>
 * <LI> to decode such an encoded string <LI>
 * <LI> to encode any filename into a safe URL (based on previous algorithm
 * but transforming, for instance, space characters to %20 instead of '+'<LI>
 * 
 * THIS CODE IS DERIVED FROM A SOURCE CODE PUBLISHED ON THE W3C WEB SITE
 * AT FOLLOWING LOCATION :
 * http://www.w3.org/International/URLUTF8Encoder.java
 * THIS ORIGINAL WORK IS PUBLISHED WITH FOLLOWING LICENSE :
 * http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 *
 * (The first two methods come from the w3c code.
 * I just changed lowercase to uppercase in the table, to be homogenous
 * with java.net.URLEncoder)
 * The goal of the third one is to provide a way to use
 * filenames in URL)
 *
 * Here are, for the first 32000 characters, the differences between
 * encode, encodeFilename and java.net.URLEncoder.encode(s,"UTF-8")
 * i char encode fn java
 * 32	 	+	%20	+
 * 33	!	!	!	%21
 * 39	'	'	'	%27
 * 40	(	(	(	%28
 * 41	)	)	)	%29
 * 47	/	%2F	/	%2F
 * 126	~	~	~	%7E
 */
public class URLUTF8Encoder {
// search for the String 'fps' for changes from the original code
final static String[] hex = { // fps
	"%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07",
	"%08", "%09", "%0A", "%0B", "%0C", "%0D", "%0E", "%0F",
	"%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17",
	"%18", "%19", "%1A", "%1B", "%1C", "%1D", "%1E", "%1F",
	"%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
	"%28", "%29", "%2A", "%2B", "%2C", "%2D", "%2E", "%2F",
	"%30", "%31", "%32", "%33", "%34", "%35", "%36", "%37",
	"%38", "%39", "%3A", "%3B", "%3C", "%3D", "%3E", "%3F",
	"%40", "%41", "%42", "%43", "%44", "%45", "%46", "%47",
	"%48", "%49", "%4A", "%4B", "%4C", "%4D", "%4E", "%4F",
	"%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57",
	"%58", "%59", "%5A", "%5B", "%5C", "%5D", "%5E", "%5F",
	"%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67",
	"%68", "%69", "%6A", "%6B", "%6C", "%6D", "%6E", "%6F",
	"%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77",
	"%78", "%79", "%7A", "%7B", "%7C", "%7D", "%7E", "%7F",
	"%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87",
	"%88", "%89", "%8A", "%8B", "%8C", "%8D", "%8E", "%8F",
	"%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97",
	"%98", "%99", "%9A", "%9B", "%9C", "%9D", "%9E", "%9F",
	"%A0", "%A1", "%A2", "%A3", "%A4", "%A5", "%A6", "%A7",
	"%A8", "%A9", "%AA", "%AB", "%AC", "%AD", "%AE", "%AF",
	"%B0", "%B1", "%B2", "%B3", "%B4", "%B5", "%B6", "%B7",
	"%B8", "%B9", "%BA", "%BB", "%BC", "%BD", "%BE", "%BF",
	"%C0", "%C1", "%C2", "%C3", "%C4", "%C5", "%C6", "%C7",
	"%C8", "%C9", "%CA", "%CB", "%CC", "%CD", "%CE", "%CF",
	"%D0", "%D1", "%D2", "%D3", "%D4", "%D5", "%D6", "%D7",
	"%D8", "%D9", "%DA", "%DB", "%DC", "%DD", "%DE", "%DF",
	"%E0", "%E1", "%E2", "%E3", "%E4", "%E5", "%E6", "%E7",
	"%E8", "%E9", "%EA", "%EB", "%EC", "%ED", "%EE", "%EF",
	"%F0", "%F1", "%F2", "%F3", "%F4", "%F5", "%F6", "%F7",
	"%F8", "%F9", "%FA", "%FB", "%FC", "%FD", "%FE", "%FF"
};

/*// fps: the table as it was in the original source code
 final static String[] hex = {
	"%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07",
	"%08", "%09", "%0a", "%0b", "%0c", "%0d", "%0e", "%0f",
	"%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17",
	"%18", "%19", "%1a", "%1b", "%1c", "%1d", "%1e", "%1f",
	"%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
	"%28", "%29", "%2a", "%2b", "%2c", "%2d", "%2e", "%2f",
	"%30", "%31", "%32", "%33", "%34", "%35", "%36", "%37",
	"%38", "%39", "%3a", "%3b", "%3c", "%3d", "%3e", "%3f",
	"%40", "%41", "%42", "%43", "%44", "%45", "%46", "%47",
	"%48", "%49", "%4a", "%4b", "%4c", "%4d", "%4e", "%4f",
	"%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57",
	"%58", "%59", "%5a", "%5b", "%5c", "%5d", "%5e", "%5f",
	"%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67",
	"%68", "%69", "%6a", "%6b", "%6c", "%6d", "%6e", "%6f",
	"%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77",
	"%78", "%79", "%7a", "%7b", "%7c", "%7d", "%7e", "%7f",
	"%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87",
	"%88", "%89", "%8a", "%8b", "%8c", "%8d", "%8e", "%8f",
	"%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97",
	"%98", "%99", "%9a", "%9b", "%9c", "%9d", "%9e", "%9f",
	"%a0", "%a1", "%a2", "%a3", "%a4", "%a5", "%a6", "%a7",
	"%a8", "%a9", "%aa", "%ab", "%ac", "%ad", "%ae", "%af",
	"%b0", "%b1", "%b2", "%b3", "%b4", "%b5", "%b6", "%b7",
	"%b8", "%b9", "%ba", "%bb", "%bc", "%bd", "%be", "%bf",
	"%c0", "%c1", "%c2", "%c3", "%c4", "%c5", "%c6", "%c7",
	"%c8", "%c9", "%ca", "%cb", "%cc", "%cd", "%ce", "%cf",
	"%d0", "%d1", "%d2", "%d3", "%d4", "%d5", "%d6", "%d7",
	"%d8", "%d9", "%da", "%db", "%dc", "%dd", "%de", "%df",
	"%e0", "%e1", "%e2", "%e3", "%e4", "%e5", "%e6", "%e7",
	"%e8", "%e9", "%ea", "%eb", "%ec", "%ed", "%ee", "%ef",
	"%f0", "%f1", "%f2", "%f3", "%f4", "%f5", "%f6", "%f7",
	"%f8", "%f9", "%fa", "%fb", "%fc", "%fd", "%fe", "%ff"
};
*/

/**
 * Encode a string to the "x-www-form-urlencoded" form, enhanced
 * with the UTF-8-in-URL proposal. This is what happens:
 *
 * <ul>
 * <li><p>The ASCII characters 'a' through 'z', 'A' through 'Z',
 *        and '0' through '9' remain the same.
 *
 * <li><p>The unreserved characters - _ . ! ~ * ' ( ) remain the same.
 *
 * <li><p>The space character ' ' is converted into a plus sign '+'.
 *
 * <li><p>All other ASCII characters are converted into the
 *        3-character string "%xy", where xy is
 *        the two-digit hexadecimal representation of the character
 *        code
 *
 * <li><p>All non-ASCII characters are encoded in two steps: first
 *        to a sequence of 2 or 3 bytes, using the UTF-8 algorithm;
 *        secondly each of these bytes is encoded as "%xx".
 * </ul>
 *
 * @param s The string to be encoded
 * @return The encoded string
 */
public static String encode(String s) {
	StringBuffer sbuf = new StringBuffer();
	int len = s.length();
	for (int i = 0; i < len; i++) {
		int ch = s.charAt(i);
		if ('A' <= ch && ch <= 'Z') {		// 'A'..'Z'
			sbuf.append((char)ch);
		} else if ('a' <= ch && ch <= 'z') {	// 'a'..'z'
			sbuf.append((char)ch);
		} else if ('0' <= ch && ch <= '9') {	// '0'..'9'
			sbuf.append((char)ch);
		} else if (ch == ' ') {			// space
			sbuf.append('+');
		} else if (ch == '-' || ch == '_'		// unreserved
				|| ch == '.' || ch == '!'
				|| ch == '~' || ch == '*'
				|| ch == '\''
				|| ch == '(' || ch == ')') {
			sbuf.append((char)ch);
		} else if (ch <= 0x007f) {		// other ASCII
			sbuf.append(hex[ch]);
		} else if (ch <= 0x07FF) {		// non-ASCII <= 0x7FF
			sbuf.append(hex[0xc0 | (ch >> 6)]);
			sbuf.append(hex[0x80 | (ch & 0x3F)]);
		} else {					// 0x7FF < ch <= 0xFFFF
			sbuf.append(hex[0xe0 | (ch >> 12)]);
			sbuf.append(hex[0x80 | ((ch >> 6) & 0x3F)]);
			sbuf.append(hex[0x80 | (ch & 0x3F)]);
		}
	}
	return sbuf.toString();
} // encode

public static String decode(String s) {
	StringBuffer sbuf = new StringBuffer () ;
	int l  = s.length() ;
	int ch = -1 ;
	int b, sumb = 0;
	for (int i = 0, more = -1 ; i < l ; i++) {
		/* Get next byte b from URL segment s */
		switch (ch = s.charAt(i)) {
			case '%':
				ch = s.charAt (++i) ;
				int hb = (Character.isDigit ((char) ch) 
						? ch - '0'
						: 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
				ch = s.charAt (++i) ;
				int lb = (Character.isDigit ((char) ch)
						? ch - '0'
						: 10+Character.toLowerCase ((char) ch)-'a') & 0xF ;
				b = (hb << 4) | lb ;
				break ;
			case '+':
				b = ' ' ;
				break ;
			default:
				b = ch ;
		}
		/* Decode byte b as UTF-8, sumb collects incomplete chars */
		if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
			sumb = (sumb << 6) | (b & 0x3f) ;	// Add 6 bits to sumb
		if (--more == 0) sbuf.append((char) sumb) ; // Add char to sbuf
		} else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
			sbuf.append((char) b) ;			// Store in sbuf
		} else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
			sumb = b & 0x1f;
			more = 1;				// Expect 1 more byte
		} else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
			sumb = b & 0x0f;
			more = 2;				// Expect 2 more bytes
		} else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
			sumb = b & 0x07;
			more = 3;				// Expect 3 more bytes
		} else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
			sumb = b & 0x03;
			more = 4;				// Expect 4 more bytes
		} else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
			sumb = b & 0x01;
			more = 5;				// Expect 5 more bytes
		}
		/* We don't test if the UTF-8 encoding is well-formed */
	}
	return sbuf.toString() ;
} // decode

// les differences par rapport a decode sont marquees d'un commentaire "//fps"
/**
 * Encode a filename so that it can be used as an URL.
 * For instance, space caracters are transformed to "%20".
 * @author fps@semanlink.net
 */
public static String encodeFilename(String s) {
	char fileSeparator = System.getProperty("file.separator").charAt(0);
	StringBuffer sbuf = new StringBuffer();
	int len = s.length();
	for (int i = 0; i < len; i++) {
		int ch = s.charAt(i);
		if ('A' <= ch && ch <= 'Z') {		// 'A'..'Z'
			sbuf.append((char)ch);
		} else if ('a' <= ch && ch <= 'z') {	// 'a'..'z'
			sbuf.append((char)ch);
		} else if ('0' <= ch && ch <= '9') {	// '0'..'9'
			sbuf.append((char)ch);
		} else if (ch == fileSeparator) {	// fps
			sbuf.append("/");
		//} else if (ch == ':') {	// fps
		//	sbuf.append(":");
		} else if (ch == ' ') {			// space
			sbuf.append("%20");			// fps
		} else if (ch == '-' || ch == '_'		// unreserved
				|| ch == '.' || ch == '!'
				|| ch == '~' || ch == '*'
				|| ch == '\''					// fps : windowze's case already handled
				|| ch == '(' || ch == ')') {
			sbuf.append((char)ch);
		
		} else if (ch <= 0x007f) {		// other ASCII
			sbuf.append(hex[ch]);
		} else if (ch <= 0x07FF) {		// non-ASCII <= 0x7FF
			sbuf.append(hex[0xc0 | (ch >> 6)]);
			sbuf.append(hex[0x80 | (ch & 0x3F)]);
		} else {					// 0x7FF < ch <= 0xFFFF
			sbuf.append(hex[0xe0 | (ch >> 12)]);
			sbuf.append(hex[0x80 | ((ch >> 6) & 0x3F)]);
			sbuf.append(hex[0x80 | (ch & 0x3F)]);
		}
	}
	return sbuf.toString();
} // encodeFilename

public static void comparaison() throws UnsupportedEncodingException {
	System.out.println("URLUTF8\tURLUTF8 fn\tURLEncoder");
	char[] c = new char[1];
	for (char i = 0; i < 32000; i++) {
		c[0] = i;
		String s = new String(c);
		String s1 = encode(s);
		String s2 = encodeFilename(s);
		String s3 = java.net.URLEncoder.encode(s, "UTF-8");
		if (
			( s1.equals(s2) )
			&& ( s1.equals(s3) )
			&& ( s2.equals(s3) )
			&& (i < 31900)
		) continue;
		System.out.println((int) i + "\t" + i + "\t" + s1 + "\t" + s2 + "\t" + s3);	
	}
	/* // le decod decode-t-il bien ?
	for (char i = 0; i < 512; i++) {
		c[0] = i;
		String s = new String(c);
		String s1 = encode(s);
		String s2 = decode(s1);
		if (
			( s.equals(s2) )
		) continue;
		System.out.println((int) i + "\t" + i + "\t" + s + "\t" + s1 + "\t" + s2);	
	}*/
}

public static void listing(char n) throws UnsupportedEncodingException {
	System.out.println("URLUTF8\tURLUTF8 fn\tURLEncoder");
	char[] c = new char[1];
	for (char i = 0; i < n; i++) {
		c[0] = i;
		String s = new String(c);
		String s1 = encode(s);
		String s2 = encodeFilename(s);
		String s3 = java.net.URLEncoder.encode(s, "UTF-8");
		System.out.println((int) i + "\t" + i + "\t" + s1 + "\t" + s2 + "\t" + s3);	
	}
}

}
