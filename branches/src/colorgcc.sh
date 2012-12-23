#!/bin/bash
for i in g++ gcc c++ cc
do
	ln -s $(which colorgcc) ~/bin/$i
done

