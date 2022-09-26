using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarerServer.WebAPI.Models.InsertModels
{
    public class PetInputModel
    {
        private string name;
        private string breed;
        private string kind;
        private Guid ownerId;

        public string Name { get => name; set => name = value; }
        public string Kind { get => kind; set => kind = value; }
        public string Breed { get => breed; set => breed = value; }
        public Guid OwnerId { get => ownerId; set => ownerId = value; }
    }
}