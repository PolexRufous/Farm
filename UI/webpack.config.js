'use strict';
const webpack = require('webpack');
const dust = require('dustjs-linkedin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = [{
    context: __dirname + '/src',
    entry: {
        buildmainpage: './mainpage/js/build.page',
        mainpage: './mainpage/js/main.page'
    },
    output: {
        path: '../application/src/main/resources/static',
        filename: 'mainpage/[name].js',
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
            },
            {
                test: /\.json$/,
                loader: 'json-loader'
            }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            dust: 'dustjs-linkedin'
        }),
        new HtmlWebpackPlugin({
            title: 'Farm Main',
            filename: 'mainpage/mainpage.html',
            template: 'mainpage/mainpage.ejs',
            chunks: ['buildmainpage', 'mainpage'],
        })
    ],

    devtool: "source-map",
    cache: false

}];
