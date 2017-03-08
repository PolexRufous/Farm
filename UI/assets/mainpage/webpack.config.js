'use strict';
var webpack = require('webpack');
var dust = require('dustjs-linkedin');
//var BrowserSyncPlugin = require('browser-sync-webpack-plugin');

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
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015', 'es2016']
                }
            },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            dust: 'dustjs-linkedin'
        }),
/*        new BrowserSyncPlugin(
            {
                host: 'localhost',
                port: 3000,
                proxy: 'http://localhost:3100/'
            }
        )*/
    ],

    watch: true,

    watchOptions: {
        aggregateTimeout: 100
    },

    devtool: "source-map"

};
