<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\User;
use App\Message;
class MessageController extends Controller
{
    public function sendmessage(Request $request)
    {
    	$validatedData = $request->validate([
    		'username' => 'required',
    		'message' => 'required'
    	]);
        //dd($validatedData);
    	//$recipient = User::where('username',$validatedData['username'])->first();
        $recipient = User::where('username', $validatedData['username'])->firstOrFail();
    	//dd($recipient);
        $finalData = [
    		'user_id' => $recipient->id,
    		'message' => $validatedData['message']
    	];
        //dd($finalData);
    	Message::create($finalData);
    	return response()->json([
    		'message' => 'Message sent to ' . $recipient->name . ' anoynymously'
    	]);
    }

    public function getmessages(Request $request)
    {
        return response()->json([
            'messages' => $request->user()->messages
        ]);
    }

    public function search(Request $request)
    {
        if (empty($request->username)) {
            return response()->json([
                'users' => []
            ], 200);
        }
        // try now
        $user = User::where('username', 'LIKE', '%' . $request->username . '%')->get();
        return response()->json([
            'users' => $user
        ],200);
    }

}
