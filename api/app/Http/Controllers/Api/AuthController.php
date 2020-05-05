<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\User;
use Auth;
class AuthController extends Controller
{
    public function register(Request $request)
    {
    	$request->username = $request->username . '@eclipsed.in';
	    $validatedData = $request->validate([
	    	'name' => 'required',
	    	'username' => 'required|unique:users',
	    	'password' => 'required|confirmed'
    	]);
    	$validatedData['password'] = bcrypt($validatedData['password']);
    	//dd($validatedData);
    	$user = User::create($validatedData);
	    $accessToken = $user->createToken('authToken')->accessToken;
	    return response()->json([
	    	'user' => $user,
	    	'access_token' => $accessToken
	    ],200);
    }
    public function login(Request $request)
    {
    	$loginData = $request->validate([
    		'username' => 'required',
    		'password' => 'required'
    	]);
    	if(!auth()->attempt($loginData))
    	{
    		return response()->json([
    			'message' => 'Invalid Credentials'
    		],401);
    	}
    	else {
	    	$accessToken = auth()->user()->createToken('authToken')->accessToken;
	    	return response()->json([
	    		'user' => auth()->user(),
	    		'access_token' => $accessToken
	    	],200);
    	}
    }
    public function profile(Request $request) {
        //return $request->user();
        return response()->json(
            $request->user()
        );
    }
    public function logout(Request $request)
    {
        $user = Auth::user()->token();
        $user->revoke();
        return response()->json(['message'=>'Logged out']);
    }
}
