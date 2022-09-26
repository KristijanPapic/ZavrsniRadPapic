using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarerServer.WebAPI.Models.InsertModels
{
    public class AppliedUserInputModel
    {
        private Guid userId;
        private Guid adId;

        public Guid UserId { get => userId; set => userId = value; }
        public Guid AdId { get => adId; set => adId = value; }
    }
}