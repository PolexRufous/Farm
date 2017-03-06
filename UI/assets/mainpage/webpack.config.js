'use strict';
var webpack = require('webpack');
var dust = require('dustjs-linkedin');

module.exports = {
    context: __dirname + '/js',
    entry: {
        buildpage: './build.page',
        mainpage: './main.page'
    },
    output: {
        path: 'public',
        filename: '[name].js',
        library: '[name]'
    },
    module: {
        loaders: [
            {
                test: /\.dust$/,
                loader: 'dust-loader'
            },
            {
                test: /\.js$/,
                loader: 'babel-loader'
            }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            dust: 'dustjs-linkedin'
        })
    ],
};
